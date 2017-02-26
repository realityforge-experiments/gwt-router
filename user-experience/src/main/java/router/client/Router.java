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
      new RouteDefinition( "#/foo", null, null, ( route, element ) -> route2( element, "foo" ), null ),
      new RouteDefinition( new RegExp( "^#/baz/(\\d+)/(\\d)$", "" ),
                           new String[]{ "bazID", "buzID" },
                           null,
                           null,
                           ( route, element ) -> route2( element, "baz2" ),
                           null ),
      new RouteDefinition( new RegExp( "^#/baz/(\\d+)$", "" ),
                           new String[]{ "bazID" },
                           ( route -> route.getRouteData().get( "bazID" ).equals( "42" ) ),
                           null,
                           ( route, element ) -> route2( element, "baz" ),
                           null ),
      new RouteDefinition( new RegExp( "^#/biz/(\\d+)$", "" ),
                           new String[]{ "bazID" },
                           ( route -> route.getRouteData().get( "bazID" ).equals( "42" ) ),
                           null,
                           ( route, element ) -> route2( element, "biz" ),
                           ( route -> info( "PostRoute " + route ) ) ),
      };

  private Node route1( final Element element )
  {
    final HTMLDivElement div = (HTMLDivElement) Global.document.createElement( "div" );
    div.innerHTML = "<h1>route1</h1>";
    return element.appendChild( div );
  }

  private Node route2( final Element element, final String string )
  {
    final HTMLDivElement div = (HTMLDivElement) Global.document.createElement( "div" );
    div.innerHTML = "<h1>" + string + "</h1>";
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
    for ( final RouteDefinition definition : _routes )
    {
      final Map<String, Object> routeData = definition.match( hash );
      if ( null != routeData )
      {
        final Route route = new Route( definition, routeData );
        final PreRouteGuardCallback preRouteGuard = definition.getPreRouteGuard();
        if ( preRouteGuard == null || preRouteGuard.preRouteGuard( route ) )
        {
          final BeforeRouteCallback beforeRoute = definition.getBeforeRoute();
          if ( null != beforeRoute )
          {
            beforeRoute.beforeRoute( route, () -> performRoute( route, rootElement ) );
          }
          else
          {
            performRoute( route, rootElement );
          }
          return;
        }
      }
    }

    JsObjects.set( Global.window.location, "hash", "#/" );
  }

  private void performRoute( @Nonnull final Route route, @Nonnull final Element rootElement )
  {
    final RouteDefinition definition = route.getDefinition();
    definition.getRoute().route( route, rootElement );
    final PostRouteCallback postRoute = definition.getPostRoute();
    if ( null != postRoute )
    {
      postRoute.postRoute( route );
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
