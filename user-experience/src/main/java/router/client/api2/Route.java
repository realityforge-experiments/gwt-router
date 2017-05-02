package router.client.api2;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;

/**
 * TODO: Add hooks to determine
 * - Should static data be added to context
 * - should routeFromPath be updated, is that a form of caching ? or is this more to do with live update
 * - should routeFromPath be cache, never, always if url matches, only if parameters
 *
 * Outcome based stuff
 * - how to create components for routeFromPath. Pass in an javax.inject.Provider?
 * - which slot or view container should component.  (This should be null if no component factory, can also be null for default)
 */
public final class Route
{
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

  @Nonnull
  private final RegExp _matcher;
  @Nullable
  private final String[] _parameterKeys;
  @Nullable
  private final GuardCallback _guard;
  @Nullable
  private final OnChangeCallbackAsync _onChange;
  @Nullable
  private final OnEnterCallbackAsync _onEnter;
  @Nullable
  private final OnLeaveCallbackAsync _onLeave;
  @Nonnull
  private final List<Route> _children;
  @Nullable
  private final Provider _provider;

  static String pathToPattern( @Nonnull final String path )
  {
    return "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$";
  }

  public Route( @Nonnull final RegExp matcher,
                @Nullable final String[] parameterKeys,
                @Nullable final GuardCallback guard,
                @Nullable final OnChangeCallbackAsync onChange,
                @Nullable final OnEnterCallbackAsync onEnter,
                @Nullable final OnLeaveCallbackAsync onLeave,
                @Nonnull final List<Route> children,
                @Nullable final Provider provider )
  {
    _parameterKeys = parameterKeys;
    _matcher = Objects.requireNonNull( matcher );
    _guard = guard;
    _onChange = onChange;
    _onEnter = onEnter;
    _onLeave = onLeave;
    _children = Collections.unmodifiableList( Objects.requireNonNull( children ) );
    _provider = provider;
  }

  @Nullable
  public OnChangeCallbackAsync getOnChange()
  {
    return _onChange;
  }

  @Nullable
  public OnEnterCallbackAsync getOnEnter()
  {
    return _onEnter;
  }

  @Nullable
  public OnLeaveCallbackAsync getOnLeave()
  {
    return _onLeave;
  }

  @Nonnull
  public List<Route> getChildren()
  {
    return _children;
  }

  @Nullable
  public Provider getProvider()
  {
    return _provider;
  }

  @Deprecated
  @Nonnull
  final RegExp getMatcher()
  {
    return _matcher;
  }

  @Deprecated
  @Nullable
  final String[] getParameterKeys()
  {
    return _parameterKeys;
  }

  @Deprecated
  @Nullable
  final GuardCallback getGuard()
  {
    return _guard;
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
}
