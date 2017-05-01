package router.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.client.backend.RoutingBackend;
import router.client.location.LocationMatch;
import router.client.location.LocationPattern;
import router.client.route.BeforeRouteCallback;
import router.client.route.Route;
import router.client.route.RouteDefinition;
import router.client.route.UpdateRouteCallback;

@SuppressWarnings( { "WeakerAccess" } )
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

  public RouteManager( @Nonnull final RoutingBackend backend, @Nonnull final Object target )
  {
    _backend = Objects.requireNonNull( backend );
    _target = Objects.requireNonNull( target );
  }

  public void addRoute( @Nonnull final RouteDefinition route )
  {
    _routes.add( Objects.requireNonNull( route ) );
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
      final LocationPattern pattern = definition.getLocation();
      final LocationMatch match = pattern.match( location );
      if ( null != match )
      {
        final Route route = new Route( match, definition );
        if ( processRoute( route ) )
        {
          _lastRoute = route;
          return route;
        }
      }
    }
    return null;
  }

  private boolean processRoute( @Nonnull final Route route )
  {
    Objects.requireNonNull( route );
    final RouteDefinition definition = route.getDefinition();
    final UpdateRouteCallback updateRoute = definition.getUpdateRoute();
    if ( null != updateRoute && null != _lastRoute && _lastRoute.getDefinition() == definition )
    {
      updateRoute.updateRoute( route );
      return true;
    }
    final BeforeRouteCallback beforeRoute = definition.getBeforeRoute();
    if ( null != beforeRoute )
    {
      beforeRoute.beforeRoute( route );
    }
    route.getDefinition().getRoute().route( route, _target );
    return true;
  }
}
