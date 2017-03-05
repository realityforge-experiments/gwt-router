package router.client;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class RouteDefinition
{
  @Nonnull
  private final RegExp _matcher;
  @Nullable
  private final String[] _parameterKeys;
  @Nonnull
  private final String _path;
  @Nullable
  private final PreRouteGuardCallback _preRouteGuard;
  @Nullable
  private final BeforeRouteCallback _beforeRoute;
  @Nonnull
  private final RouteCallback _route;
  @Nonnull
  private final UpdateRouteCallback _updateRoute;
  @Nullable
  private final PostRouteCallback _postRoute;

  @SuppressWarnings( "ConstantConditions" )
  public RouteDefinition( @Nonnull final String path,
                          @Nullable final PreRouteGuardCallback preRouteGuard,
                          @Nullable final BeforeRouteCallback beforeRoute,
                          @Nonnull final RouteCallback route,
                          @Nullable final UpdateRouteCallback updateRoute,
                          @Nullable final PostRouteCallback postRoute )
  {
    assert null != path;
    assert null != route;
    _path = path;

    final String regex =
      "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$";
    _matcher = new RegExp( regex );
    _parameterKeys = null;
    _preRouteGuard = preRouteGuard;
    _beforeRoute = beforeRoute;
    _route = route;
    _updateRoute = updateRoute;
    _postRoute = postRoute;
  }

  @SuppressWarnings( "ConstantConditions" )
  public RouteDefinition( @Nonnull final RegExp matcher,
                          @Nonnull final String[] parameterKeys,
                          @Nullable final PreRouteGuardCallback preRouteGuard,
                          @Nullable final BeforeRouteCallback beforeRoute,
                          @Nonnull final RouteCallback route,
                          @Nullable final UpdateRouteCallback updateRoute,
                          @Nullable final PostRouteCallback postRoute )
  {
    assert null != matcher;
    assert null != parameterKeys;
    assert null != route;
    _path = matcher.toString();
    _parameterKeys = parameterKeys;
    _matcher = matcher;
    _preRouteGuard = preRouteGuard;
    _beforeRoute = beforeRoute;
    _route = route;
    _updateRoute = updateRoute;
    _postRoute = postRoute;
  }

  @Nonnull
  public String getPath()
  {
    return _path;
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

  @Nonnull
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
  public Map<String, Object> match( @Nonnull final String hash )
  {
    assert null != hash;
    final String[] groups = _matcher.exec( hash );
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
