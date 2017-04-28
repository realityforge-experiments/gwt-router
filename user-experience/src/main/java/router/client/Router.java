package router.client;

import com.google.gwt.core.client.EntryPoint;
import elemental2.Element;
import elemental2.Global;
import java.util.Objects;
import router.client.backend.Elemental2RoutingBackend;
import router.client.backend.GwtFrameworkRoutingBackend;
import router.client.backend.RoutingBackend;
import router.client.location.LocationDefinition;
import router.client.location.LocationPattern;
import router.client.location.RegExp;
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
        new RouteDefinition( new LocationDefinition( new LocationPattern( "/" ), null ),
                             null,
                             ( route, element ) -> route2( element, "/" ),
                             null ),
        new RouteDefinition( new LocationDefinition( new LocationPattern( "/foo" ), null ),
                             null,
                             ( route, element ) -> route2( element, "/foo" ),
                             null ),
        new RouteDefinition( new LocationDefinition( new LocationPattern( new RegExp( "^/baz/(\\d+)/(\\d+)$" ),
                                                                          new String[]{ "bazID", "buzID" } ), null ),
                             null,
                             ( route, element ) -> route2( element,
                                                           "/baz/" +
                                                           route.getData( "bazID" ) +
                                                           "/" +
                                                           route.getData( "buzID" ) ),
                             null ),
        new RouteDefinition( new LocationDefinition( new LocationPattern( new RegExp( "^/baz/(\\d+)$" ),
                                                                          new String[]{ "bazID" } ),
                                                     ( route -> Objects.equals( route.getParameter( "bazID" ), "42" ) ) ),
                             null,
                             ( route, element ) -> route2( element, "/baz/" + route.getData( "bazID" ) ),
                             null ),
        new RouteDefinition( new LocationDefinition( new LocationPattern( new RegExp( "^/biz/(\\d+)$" ),
                                                                          new String[]{ "bazID" } ),
                                                     ( route -> Objects.equals( route.getParameter( "bazID" ), "42" ) ) ),
                             null,
                             ( route, element ) -> route2( element, "/biz/" + route.getData( "bazID" ) ),
                             null ),
        new RouteDefinition( new LocationDefinition( new LocationPattern( new RegExp( "^/ding/(\\d+)$" ),
                                                                          new String[]{ "bazID" } ),
                                                     null ),
                             null,
                             ( route, element ) -> route2( element, "/ding/" + route.getData( "bazID" ) ),
                             route -> route2( Global.document.getElementById( "hook" ),
                                              "/ding/" + route.getData( "bazID" ) + " (NoRoute)" ) ),
        new RouteDefinition( new LocationDefinition( new LocationPattern( new RegExp( "^/end$" ), new String[ 0 ] ),
                                                     null ), null, ( route, element ) ->
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
