package router.client;

import com.google.gwt.core.client.EntryPoint;
import elemental2.Element;
import elemental2.EventTarget;
import elemental2.Global;
import elemental2.HTMLDivElement;
import elemental2.HashChangeEvent;
import elemental2.Node;
import elemental2.RegExp;
import java.util.Map;
import javax.annotation.Nonnull;

public final class Router
  implements EntryPoint
{
  private final RouteDefinition[] _routes = new RouteDefinition[]
    {
      new RouteDefinition( "#/", null, null, ( route, element ) -> route1( element ), null ),
      new RouteDefinition( "#/foo", null, null, ( route, element ) -> route2( element ), null ),
      new RouteDefinition( new RegExp( "^#/baz/(\\d+)/(\\d)$", "" ),
                           new String[]{ "bazID", "buzID" },
                           null,
                           null,
                           ( route, element ) -> route2( element ),
                           null ),
      new RouteDefinition( new RegExp( "^#/baz/(\\d+)$", "" ),
                           new String[]{ "bazID" },
                           null,
                           null,
                           ( route, element ) -> route2( element ),
                           null )
    };

  private Node route1( final Element element )
  {
    final HTMLDivElement div = (HTMLDivElement) Global.document.createElement( "div" );
    div.innerHTML = "<h1>route1</h1>";
    return element.appendChild( div );
  }

  private Node route2( final Element element )
  {
    final HTMLDivElement div = (HTMLDivElement) Global.document.createElement( "div" );
    div.innerHTML = "<h1>route2</h1>";
    return element.appendChild( div );
  }

  public void onModuleLoad()
  {
    final EventTarget.AddEventListenerListenerCallback callback = ( a ) -> route( (HashChangeEvent) a );
    Global.window.addEventListener( "hashchange", callback, false );
    try
    {
      info( "Router started" );
      route();
    }
    catch ( final Exception e )
    {
      warn( "Unexpected problem initializing the Router application: " + e );
      Global.window.alert( "Error: " + e.getMessage() );
    }
  }

  private boolean route( @Nonnull final HashChangeEvent e )
  {
    info( "event.newURL: " + e.newURL );
    info( "event.oldURL: " + e.oldURL );
    route();
    return true;
  }

  private void route()
  {
    final String hash = JsObjects.get( Global.window.location, "hash" );
    info( "location.hash: " + hash );

    final Element rootElement = Global.document.getElementById( "hook" );
    for ( final RouteDefinition route : _routes )
    {
      info( "Match? " + hash + " against " + route.getPath() );
      final Map<String, Object> routeData = route.match( hash );
      if ( null != routeData )
      {
        info( "Matched " + route.getPath() + " routeData: " + routeData );
        final RouteCallback routeCallback = route.getRoute();
        routeCallback.route( new Route( route, routeData ), rootElement );
        return;
      }
    }

    JsObjects.set( Global.window.location, "hash", "#/" );
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
