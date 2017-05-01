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
    this( new RegExp( pathToPattern( path ) ), new String[ 0 ] );
  }

  static String pathToPattern( @Nonnull final String path )
  {
    return "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$";
  }

  public LocationPattern( @Nonnull final RegExp matcher,
                          @Nullable final String[] parameterKeys )
  {
    _parameterKeys = parameterKeys;
    _matcher = Objects.requireNonNull( matcher );
  }

  @Nullable
  public Map<String, Object> match( @Nonnull final String location )
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
      return matchData;
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
}
