package router.client.api2;

import org.testng.annotations.Test;
import router.client.location.LocationMatch;
import static org.mockito.Mockito.*;

public final class OnEnterCallbackAsyncAdapterTest
{
  @Test
  public void onEnter_proceed()
  {
    final OnEnterControl control = mock( OnEnterControl.class );
    final OnEnterCallback callback = mock( OnEnterCallback.class );
    final OnEnterCallbackAsyncAdapter adapter = new OnEnterCallbackAsyncAdapter( callback );

    final LocationMatch match = FactoryUtil.createLocation();

    adapter.onEnter( match, control );
    verify( callback ).onEnter( match );
    verify( control ).proceed();
  }
}
