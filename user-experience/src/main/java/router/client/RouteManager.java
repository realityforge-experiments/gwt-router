package router.client;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings( { "ConstantConditions", "WeakerAccess" } )
public final class RouteManager
{
  private final RoutingBackend _backend;
  @Nonnull
  private final RouteDefinition[] _routes;
  @Nonnull
  private final Object _target;
  @Nullable
  private String _defaultHash;
  @Nullable
  private Route _lastRoute;
  private Object _callback;

  public RouteManager( @Nonnull final RoutingBackend backend,
                       @Nonnull final RouteDefinition[] routes,
                       @Nonnull final Object target,
                       @Nullable final String defaultHash )
  {
    assert null != backend;
    assert null != routes;
    assert null != target;
    assert Stream.of( routes ).noneMatch( Objects::isNull );

    _backend = backend;
    _routes = routes;
    _target = target;
    _defaultHash = defaultHash;
  }

  public void install()
  {
    uninstall();
    _callback = _backend.addListener( h -> onHashChange() );
  }

  public void uninstall()
  {
    if ( null != _callback )
    {
      _backend.removeListener( _callback );
      _callback = null;
    }
  }

  private boolean onHashChange()
  {
    route();
    return true;
  }

  @Nullable
  public Route route()
  {
    final String hash = _backend.getHash();

    final Route route = attemptRouteMatch( hash );
    if ( null != route )
    {
      return route;
    }
    else if ( null != _defaultHash )
    {
      _backend.setHash( _defaultHash );
      return attemptRouteMatch( hash );
    }
    else
    {
      return null;
    }
  }

  private Route attemptRouteMatch( final String hash )
  {
    for ( final RouteDefinition definition : _routes )
    {
      final Map<String, Object> routeData = definition.match( hash );
      final Route route = new Route( definition, routeData );
      if ( null != routeData && processRoute( route ) )
      {
        _lastRoute = route;
        return route;
      }
    }
    return null;
  }

  private boolean processRoute( @Nonnull final Route route )
  {
    assert null != route;
    final RouteDefinition definition = route.getDefinition();
    final PreRouteGuardCallback preRouteGuard = definition.getPreRouteGuard();
    if ( preRouteGuard == null || preRouteGuard.preRouteGuard( route ) )
    {
      if ( null != _lastRoute && _lastRoute.getDefinition() == definition )
      {
        final UpdateRouteCallback updateRoute = definition.getUpdateRoute();
        if ( null != updateRoute )
        {
          updateRoute.updateRoute( route );
          return true;
        }
      }
      final BeforeRouteCallback beforeRoute = definition.getBeforeRoute();
      if ( null != beforeRoute )
      {
        beforeRoute.beforeRoute( route, () -> completeRouting( route ) );
      }
      else
      {
        completeRouting( route );
      }
      return true;
    }
    else
    {
      return false;
    }
  }

  private void completeRouting( @Nonnull final Route route )
  {
    final RouteDefinition definition = route.getDefinition();
    definition.getRoute().route( route, _target );
    final PostRouteCallback postRoute = definition.getPostRoute();
    if ( null != postRoute )
    {
      postRoute.postRoute( route );
    }
  }
}
