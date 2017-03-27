package router.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class RouteDefinition
{
  @Nonnull
  private final RegExp _matcher;
  @Nullable
  private final String[] _parameterKeys;
  @Nullable
  private final PreRouteGuardCallback _preRouteGuard;
  @Nullable
  private final BeforeRouteCallback _beforeRoute;
  @Nonnull
  private final RouteCallback _route;
  @Nullable
  private final UpdateRouteCallback _updateRoute;
  @Nullable
  private final PostRouteCallback _postRoute;

  public RouteDefinition( @Nonnull final String path,
                          @Nullable final PreRouteGuardCallback preRouteGuard,
                          @Nullable final BeforeRouteCallback beforeRoute,
                          @Nonnull final RouteCallback route,
                          @Nullable final UpdateRouteCallback updateRoute,
                          @Nullable final PostRouteCallback postRoute )
  {
    this( pathToRegex( path ), new String[ 0 ], preRouteGuard, beforeRoute, route, updateRoute, postRoute );
  }

  static RegExp pathToRegex( @Nonnull final String path )
  {
    return new RegExp( "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$" );
  }

  public RouteDefinition( @Nonnull final RegExp matcher,
                          @Nonnull final String[] parameterKeys,
                          @Nullable final PreRouteGuardCallback preRouteGuard,
                          @Nullable final BeforeRouteCallback beforeRoute,
                          @Nonnull final RouteCallback route,
                          @Nullable final UpdateRouteCallback updateRoute,
                          @Nullable final PostRouteCallback postRoute )
  {
    _parameterKeys = Objects.requireNonNull( parameterKeys );
    _matcher = Objects.requireNonNull( matcher );
    _preRouteGuard = preRouteGuard;
    _beforeRoute = beforeRoute;
    _route = Objects.requireNonNull( route );
    _updateRoute = updateRoute;
    _postRoute = postRoute;
  }

  @Nullable
  public PreRouteGuardCallback getPreRouteGuard()
  {
    return _preRouteGuard;
  }

  @Nullable
  public BeforeRouteCallback getBeforeRoute()
  {
    return _beforeRoute;
  }

  @Nonnull
  public RouteCallback getRoute()
  {
    return _route;
  }

  @Nullable
  public UpdateRouteCallback getUpdateRoute()
  {
    return _updateRoute;
  }

  @Nullable
  public PostRouteCallback getPostRoute()
  {
    return _postRoute;
  }

  @SuppressWarnings( "ConstantConditions" )
  @Nullable
  public Map<String, Object> match( @Nonnull final String location )
  {
    assert null != location;
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
