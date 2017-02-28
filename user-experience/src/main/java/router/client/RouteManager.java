package router.client;

import elemental2.Element;
import elemental2.Global;
import elemental2.Window;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings( { "ConstantConditions", "WeakerAccess" } )
public final class RouteManager
{
  @Nonnull
  private final RouteDefinition[] _routes;
  @Nonnull
  private final Element _rootElement;
  @Nullable
  private String _defaultHash;
  @Nullable
  private Route _lastRoute;
  private Object _callback;

  public RouteManager( @Nonnull final RouteDefinition[] routes,
                       @Nonnull final Element rootElement,
                       @Nullable final String defaultHash )
  {
    assert null != routes;
    assert null != rootElement;
    assert Stream.of( routes ).noneMatch( Objects::isNull );

    _routes = routes;
    _rootElement = rootElement;
    _defaultHash = defaultHash;
  }

  public void install()
  {
    uninstall();
    final Window.AddEventListenerListenerCallback callback = ( e ) -> onHashChange();
    Global.window.addEventListener( "hashchange", callback, false );
    _callback = callback;
  }

  public void uninstall()
  {
    if( null != _callback )
    {
      Global.window.removeEventListener( "hashchange", (Window.RemoveEventListenerListenerCallback) _callback, false );
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
    final String hash = JsObjects.get( Global.window.location, "hash" );

    final Route route = attemptRouteMatch( hash );
    if ( null != route )
    {
      return route;
    }
    else if ( null != _defaultHash )
    {
      JsObjects.set( Global.window.location, "hash", _defaultHash );
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
      if( null != _lastRoute && _lastRoute.getDefinition() == definition )
      {
        final UpdateRouteCallback updateRoute = definition.getUpdateRoute();
        if( null != updateRoute )
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
    definition.getRoute().route( route, _rootElement );
    final PostRouteCallback postRoute = definition.getPostRoute();
    if ( null != postRoute )
    {
      postRoute.postRoute( route );
    }
  }
}
