package router.client.api2.backend;

import elemental2.dom.DomGlobal;
import elemental2.dom.EventListener;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Elemental2RoutingBackend
  implements RoutingBackend
{
  @Nullable
  @Override
  public String getHash()
  {
    final String hash = JsObjects.get( DomGlobal.window.location, "hash" );
    return null == hash ? null : hash.substring( 1 );
  }

  @Override
  public void setHash( @Nullable final String hash )
  {
    JsObjects.set( DomGlobal.window.location, "hash", null == hash ? null : "#" + hash );
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
