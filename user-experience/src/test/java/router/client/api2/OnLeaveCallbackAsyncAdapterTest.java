package router.client.api2;

import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

public final class OnLeaveCallbackAsyncAdapterTest
{
  @Test
  public void onLeave_proceed()
  {
    final OnLeaveControl control = mock( OnLeaveControl.class );
    final OnLeaveCallback callback = mock( OnLeaveCallback.class );
    final OnLeaveCallbackAsyncAdapter adapter = new OnLeaveCallbackAsyncAdapter( callback );

    final TestContext context = new TestContext();
    final Route route = FactoryUtil.createRoute();

    adapter.onLeave( context, route, control );
    verify( callback ).onLeave( context, route );
    verify( control ).proceed();
  }
}
