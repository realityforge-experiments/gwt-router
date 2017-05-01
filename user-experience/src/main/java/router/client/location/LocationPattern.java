package router.client.location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class LocationPattern
{
  @Nonnull
  private final RegExp _matcher;
  @Nullable
  private final String[] _parameterKeys;
  @Nullable
  private final GuardCallback _guard;

  public LocationPattern( @Nonnull final String path )
  {
    this( new RegExp( pathToPattern( path ) ) );
  }

  static String pathToPattern( @Nonnull final String path )
  {
    return "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$";
  }

  public LocationPattern( @Nonnull final RegExp matcher )
  {
    this( matcher, null );
  }

  public LocationPattern( @Nonnull final RegExp matcher, @Nullable final String[] parameterKeys )
  {
    this( matcher, parameterKeys, null );
  }

  public LocationPattern( @Nonnull final RegExp matcher,
                          @Nullable final String[] parameterKeys,
                          @Nullable final GuardCallback guard )
  {
    _parameterKeys = parameterKeys;
    _matcher = Objects.requireNonNull( matcher );
    _guard = guard;
  }

  @Nullable
  public LocationMatch match( @Nonnull final String location )
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
        return new LocationMatch( location, this, matchData );
      }
      else
      {
        return null;
      }
    }
    else
    {
      return null;
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
                         @Nonnull LocationPattern pattern,
                         @Nonnull Map<String, Object> matchData );
  }
}
