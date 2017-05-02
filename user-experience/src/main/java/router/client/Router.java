package router.client;

import com.google.gwt.core.client.EntryPoint;
import elemental2.Element;
import elemental2.Global;
import java.util.Objects;
import router.client.api2.Routes;
import router.client.api2.backend.Elemental2RoutingBackend;
import router.client.api2.backend.GwtFrameworkRoutingBackend;
import router.client.api2.backend.RoutingBackend;
import router.client.route.RouteDefinition;

public final class Router
  implements EntryPoint
{
  private RouteManager _routeManager;

  private void route2( final Object element, final String string )
  {
    ( (Element) element ).innerHTML = "<h1>" + string + "</h1>";
  }

  public void onModuleLoad()
  {
    final Element rootElement = Global.document.getElementById( "hook" );

    final RouteDefinition[] routes = new RouteDefinition[]
      {
        new RouteDefinition( Routes.routeFromPath( "/" ).build(),
                             null,
                             ( route, element ) -> route2( element, "/" ),
                             null ),
        new RouteDefinition( Routes.routeFromPath( "/foo" ).build(),
                             null,
                             ( route, element ) -> route2( element, "/foo" ),
                             null ),
        new RouteDefinition( Routes.route( "^/baz/(\\d+)/(\\d+)$" ).
          setParameterKeys( new String[]{ "bazID", "buzID" } ).
                                     build(),
                             null,
                             ( route, element ) -> route2( element,
                                                           "/baz/" +
                                                           route.getData( "bazID" ) +
                                                           "/" +
                                                           route.getData( "buzID" ) ),
                             null ),
        new RouteDefinition( Routes.route( "^/baz/(\\d+)$" ).
          setParameterKeys( new String[]{ "bazID" } ).
                                     setGuard( ( ( l, p, data ) -> Objects.equals( data.get( "bazID" ), "42" ) ) ).
                                     build(),
                             null,
                             ( route, element ) -> route2( element, "/baz/" + route.getData( "bazID" ) ),
                             null ),
        new RouteDefinition( Routes.route( "^/biz/(\\d+)$" ).
          setParameterKeys( new String[]{ "bazID" } ).
                                     setGuard( ( ( l, p, data ) -> Objects.equals( data.get( "bazID" ), "42" ) ) ).
                                     build(),
                             null,
                             ( route, element ) -> route2( element, "/biz/" + route.getData( "bazID" ) ),
                             null ),
        new RouteDefinition( Routes.route( "^/ding/(\\d+)$" ).
          setParameterKeys( new String[]{ "bazID" } ).build(),
                             null,
                             ( route, element ) -> route2( element, "/ding/" + route.getData( "bazID" ) ),
                             route -> route2( Global.document.getElementById( "hook" ),
                                              "/ding/" + route.getData( "bazID" ) + " (NoRoute)" ) ),
        new RouteDefinition( Routes.route( "^/end$" ).build(),
                             null,
                             ( route, element ) ->
                             {
                               route2( element, "/end" );
                               _routeManager.uninstall();
                             },
                             null ),
        };

    final boolean useElemental = true;
    @SuppressWarnings( "ConstantConditions" ) final RoutingBackend backend =
      useElemental ? new Elemental2RoutingBackend() : new GwtFrameworkRoutingBackend();
    _routeManager = new RouteManager( backend, rootElement );
    _routeManager.setDefaultLocation( "/" );
    for ( final RouteDefinition route : routes )
    {
      _routeManager.addRoute( route );
    }
    _routeManager.install();
    try
    {
      info( "Router started" );
      _routeManager.route();
    }
    catch ( final Exception e )
    {
      warn( "Unexpected problem initializing the Router application: " + e );
      Global.window.alert( "Error: " + e.getMessage() );
    }
  }

  public static native void error( String message ) /*-{
    window.console.error( message );
  }-*/;

  public static native void warn( String message ) /*-{
    window.console.warn( message );
  }-*/;

  public static native void info( String message ) /*-{
    window.console.info( message );
  }-*/;

  public static native void log( String message ) /*-{
    window.console.log( message );
  }-*/;
}
