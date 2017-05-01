package router.client.api2;

import org.testng.annotations.Test;
import router.client.location.Route;
import static org.mockito.Mockito.*;

public final class OnEnterCallbackAsyncAdapterTest
{
  @Test
  public void onEnter_proceed()
  {
    final OnEnterControl control = mock( OnEnterControl.class );
    final OnEnterCallback callback = mock( OnEnterCallback.class );
    final OnEnterCallbackAsyncAdapter adapter = new OnEnterCallbackAsyncAdapter( callback );

    final TestContext context = new TestContext();
    final Route route = FactoryUtil.createRoute();

    adapter.onEnter( context, route, control );
    verify( callback ).onEnter( context, route );
    verify( control ).proceed();
  }
}
