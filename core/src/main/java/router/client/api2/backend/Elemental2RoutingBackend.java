package router.client.api2.backend;

import elemental2.dom.DomGlobal;
import elemental2.dom.EventListener;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.base.JsPropertyMap;

public class Elemental2RoutingBackend
  implements RoutingBackend
{
  @Nullable
  @Override
  public String getHash()
  {
    final String hash = (String) JsPropertyMap.of( DomGlobal.window.location ).get( "hash" );
    return null == hash ? null : hash.substring( 1 );
  }

  @Override
  public void setHash( @Nullable final String hash )
  {
    final String value = null == hash ? null : "#" + hash;
    JsPropertyMap.of( DomGlobal.window.location ).set( "hash", value );
  }

  @Override
  public Object addListener( @Nonnull final HashChangeListener handler )
  {
    final EventListener eventListener = e -> handler.onHashChange( getHash() );
    DomGlobal.window.addEventListener( "hashchange", eventListener, false );
    return eventListener;
  }

  @Override
  public void removeListener( final Object handle )
  {
    DomGlobal.window.removeEventListener( "hashchange", (EventListener) handle, false );
  }
}
