package router.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings( { "ConstantConditions", "WeakerAccess", "SameParameterValue" } )
public final class RouteManager
{
  private final RoutingBackend _backend;
  @Nonnull
  private final List<RouteDefinition> _routes = new ArrayList<>();
  @Nonnull
  private final Object _target;
  @Nullable
  private String _defaultLocation;
  @Nullable
  private Route _lastRoute;
  private Object _callback;

  public RouteManager( @Nonnull final RoutingBackend backend,
                       @Nonnull final Object target )
  {
    assert null != backend;
    assert null != target;

    _backend = backend;
    _target = target;
  }

  public void addRoute( @Nonnull final RouteDefinition route )
  {
    assert null != route;
    _routes.add( route );
  }

  @Nullable
  public String getDefaultLocation()
  {
    return _defaultLocation;
  }

  public void setDefaultLocation( @Nullable final String defaultLocation )
  {
    _defaultLocation = defaultLocation;
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
    final String location = _backend.getHash();

    final Route route = attemptRouteMatch( location );
    if ( null != route )
    {
      return route;
    }
    else if ( null != _defaultLocation )
    {
      _backend.setHash( _defaultLocation );
      return attemptRouteMatch( location );
    }
    else
    {
      return null;
    }
  }

  private Route attemptRouteMatch( final String location )
  {
    for ( final RouteDefinition definition : _routes )
    {
      final Map<String, Object> routeData = definition.match( location );
      final Route route = new Route( location, definition, routeData );
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
