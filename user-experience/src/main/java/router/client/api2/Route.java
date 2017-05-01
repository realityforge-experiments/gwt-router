package router.client.api2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Route
{
  @Nonnull
  private final RegExp _matcher;
  @Nullable
  private final String[] _parameterKeys;
  @Nullable
  private final GuardCallback _guard;
  @Nullable
  private OnLeaveCallbackAsync _onLeave;
  @Nullable
  private OnEnterCallbackAsync _onEnter;
  @Nullable
  private OnChangeCallbackAsync _onChange;

  public Route( @Nonnull final String path )
  {
    this( new RegExp( pathToPattern( path ) ) );
  }

  static String pathToPattern( @Nonnull final String path )
  {
    return "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$";
  }

  public Route( @Nonnull final RegExp matcher )
  {
    this( matcher, null );
  }

  public Route( @Nonnull final RegExp matcher, @Nullable final String[] parameterKeys )
  {
    this( matcher, parameterKeys, null );
  }

  public Route( @Nonnull final RegExp matcher,
                @Nullable final String[] parameterKeys,
                @Nullable final GuardCallback guard )
  {
    _parameterKeys = parameterKeys;
    _matcher = Objects.requireNonNull( matcher );
    _guard = guard;
  }

  @Nullable
  public OnLeaveCallbackAsync getOnLeave()
  {
    return _onLeave;
  }

  public void setOnLeave( @Nullable final OnLeaveCallbackAsync onLeave )
  {
    _onLeave = onLeave;
  }

  @Nullable
  public OnEnterCallbackAsync getOnEnter()
  {
    return _onEnter;
  }

  public void setOnEnter( @Nullable final OnEnterCallbackAsync onEnter )
  {
    _onEnter = onEnter;
  }

  @Nullable
  public OnChangeCallbackAsync getOnChange()
  {
    return _onChange;
  }

  public void setOnChange( @Nullable final OnChangeCallbackAsync onChange )
  {
    _onChange = onChange;
  }

  public boolean match( @Nonnull final RouteContext context, @Nonnull final String location )
  {
    Objects.requireNonNull( location );
    final String[] groups = _matcher.exec( location );
    if ( null != groups )
    {
      final HashMap<String, Object> matchData = new HashMap<>();
      //Group 0 is the whole string so we can skip it
      for ( int i = 1; i < groups.length; i++ )
      {
        final String value = groups[ i ];
        final int paramIndex = i - 1;
        final String key = toKey( paramIndex );

        matchData.put( key, value );
      }
      if ( null == _guard || _guard.shouldMatch( location, this, matchData ) )
      {
        context.getParameters().putAll( matchData );
        return true;
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
  }

  @Nonnull
  String toKey( final int paramIndex )
  {
    return null == _parameterKeys || _parameterKeys.length <= paramIndex || null == _parameterKeys[ paramIndex ] ?
           "p" + paramIndex :
           _parameterKeys[ paramIndex ];
  }

  /**
   * This callback is final check to see if the pattern matches.
   * The guard can modify the matchData.
   */
  @FunctionalInterface
  public interface GuardCallback
  {
    boolean shouldMatch( @Nonnull String location,
                         @Nonnull Route route,
                         @Nonnull Map<String, Object> matchData );
  }
}
