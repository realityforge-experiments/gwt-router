package router.client.api2;

import org.testng.annotations.Test;
import router.client.location.LocationMatch;
import static org.mockito.Mockito.*;

public final class OnLeaveCallbackAsyncAdapterTest
{
  @Test
  public void onLeave_proceed()
  {
    final OnLeaveControl control = mock( OnLeaveControl.class );
    final OnLeaveCallback callback = mock( OnLeaveCallback.class );
    final OnLeaveCallbackAsyncAdapter adapter = new OnLeaveCallbackAsyncAdapter( callback );

    final LocationMatch match = FactoryUtil.createLocation();

    adapter.onLeave( match, control );
    verify( callback ).onLeave( match );
    verify( control ).proceed();
  }
}
