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

  public LocationPattern( @Nonnull final String path )
  {
    this( pathToRegex( path ), new String[ 0 ] );
  }

  static RegExp pathToRegex( @Nonnull final String path )
  {
    return new RegExp( "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$" );
  }

  public LocationPattern( @Nonnull final RegExp matcher,
                          @Nonnull final String[] parameterKeys )
  {
    _parameterKeys = Objects.requireNonNull( parameterKeys );
    _matcher = Objects.requireNonNull( matcher );
  }

  @Nullable
  public Map<String, Object> match( @Nonnull final String location )
  {
    Objects.requireNonNull( location );
    final String[] groups = _matcher.exec( location );
    if ( null != groups )
    {
      final HashMap<String, Object> routeData = new HashMap<>();
      //Group 0 is the whole string so we can skip it
      for ( int i = 1; i < groups.length; i++ )
      {
        final String value = groups[ i ];
        final int paramIndex = i - 1;
        final String key = toKey( paramIndex );

        routeData.put( key, value );
      }
      return routeData;
    }
    else
    {
      return null;
    }
  }

  private String toKey( final int paramIndex )
  {
    return _parameterKeys == null || _parameterKeys.length <= paramIndex ?
           "p" + paramIndex :
           _parameterKeys[ paramIndex ];
  }
}
