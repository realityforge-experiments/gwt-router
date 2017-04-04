package router.client.backend;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import javax.annotation.Nullable;

public class GwtFrameworkRoutingBackend
  implements RoutingBackend
{
  @Nullable
  @Override
  public String getHash()
  {
    final String hash = Window.Location.getHash();
    return null == hash ? null : hash.substring( 1 );
  }

  @Override
  public void setHash( @Nullable final String hash )
  {
    final String hash1 = null == hash ? null : "#" + hash;
    Window.Location.assign( Window.Location.createUrlBuilder().setHash( hash1 ).buildString() );
  }

  @Override
  public Object addListener( final HashChangeListener handler )
  {
    return History.addValueChangeHandler( event -> handler.onHashChange( event.getValue() ) );
  }

  @Override
  public void removeListener( final Object handle )
  {
    ( (HandlerRegistration) handle ).removeHandler();
  }
}
