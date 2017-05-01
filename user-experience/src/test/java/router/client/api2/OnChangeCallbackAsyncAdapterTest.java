package router.client.api2;

import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import router.client.location.Route;
import static org.mockito.Mockito.*;

public final class OnChangeCallbackAsyncAdapterTest
{
  @Test
  public void onChange_proceed()
  {
    final OnChangeControl control = mock( OnChangeControl.class );
    final OnChangeCallback callback = mock( OnChangeCallback.class );
    final OnChangeCallbackAsyncAdapter adapter = new OnChangeCallbackAsyncAdapter( callback );

    final TestContext context = new TestContext();
    final Route route = FactoryUtil.createRoute();
    final String previousLocation = ValueUtil.randomString();

    when( callback.onChange( context, previousLocation, route ) ).thenReturn( Boolean.TRUE );
    adapter.onChange( context, previousLocation, route, control );
    verify( callback ).onChange( context, previousLocation, route );
    verify( control ).proceed();
  }

  @Test
  public void onChange_abort()
  {
    final OnChangeControl control = mock( OnChangeControl.class );
    final OnChangeCallback callback = mock( OnChangeCallback.class );
    final OnChangeCallbackAsyncAdapter adapter = new OnChangeCallbackAsyncAdapter( callback );

    final TestContext context = new TestContext();
    final Route route = FactoryUtil.createRoute();
    final String previousLocation = ValueUtil.randomString();

    when( callback.onChange( context, previousLocation, route ) ).thenReturn( Boolean.FALSE );
    adapter.onChange( context, previousLocation, route, control );
    verify( callback ).onChange( context, previousLocation, route );
    verify( control ).abort();
  }
}
