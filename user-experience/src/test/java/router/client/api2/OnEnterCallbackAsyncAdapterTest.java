package router.client.api2;

import org.testng.annotations.Test;
import router.client.location.Location;
import static org.mockito.Mockito.*;

public final class OnEnterCallbackAsyncAdapterTest
{
  @Test
  public void onEnter_proceed()
  {
    final OnEnterControl control = mock( OnEnterControl.class );
    final OnEnterCallback callback = mock( OnEnterCallback.class );
    final OnEnterCallbackAsyncAdapter adapter = new OnEnterCallbackAsyncAdapter( callback );

    final Location location = FactoryUtil.createLocation();

    adapter.onEnter( location, control );
    verify( callback ).onEnter( location );
    verify( control ).proceed();
  }
}
