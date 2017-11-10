package router.client.api2.backend;

import elemental2.Global;
import elemental2.Window;
import javax.annotation.Nullable;

public class Elemental2RoutingBackend
  implements RoutingBackend
{
  @Nullable
  @Override
  public String getHash()
  {
    final String hash = JsObjects.get( Global.window.location, "hash" );
    return null == hash ? null : hash.substring( 1 );
  }

  @Override
  public void setHash( @Nullable final String hash )
  {
    JsObjects.set( Global.window.location, "hash", null == hash ? null : "#" + hash );
  }

  @Override
  public Object addListener( final HashChangeListener handler )
  {
    final Window.AddEventListenerListenerCallback callback = ( e ) -> handler.onHashChange( getHash() );
    Global.window.addEventListener( "hashchange", callback, false );
    return callback;
  }

  @Override
  public void removeListener( final Object handle )
  {
    Global.window.removeEventListener( "hashchange", (Window.RemoveEventListenerListenerCallback) handle, false );
  }
}
