package router.client.api2;

import org.testng.annotations.Test;
import router.client.location.Location;
import static org.mockito.Mockito.*;

public final class OnLeaveCallbackAsyncAdapterTest
{
  @Test
  public void onLeave_proceed()
  {
    final OnLeaveControl control = mock( OnLeaveControl.class );
    final OnLeaveCallback callback = mock( OnLeaveCallback.class );
    final OnLeaveCallbackAsyncAdapter adapter = new OnLeaveCallbackAsyncAdapter( callback );

    final Location location = FactoryUtil.createLocation();

    adapter.onLeave( location, control );
    verify( callback ).onLeave( location );
    verify( control ).proceed();
  }
}
