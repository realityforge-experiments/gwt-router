package router.client.api2;

import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import router.client.location.Location;
import static org.mockito.Mockito.*;

public final class OnChangeCallbackAsyncAdapterTest
{
  @Test
  public void onChange_proceed()
  {
    final OnChangeControl control = mock( OnChangeControl.class );
    final OnChangeCallback callback = mock( OnChangeCallback.class );
    final OnChangeCallbackAsyncAdapter adapter = new OnChangeCallbackAsyncAdapter( callback );

    final Location nextLocation = FactoryUtil.createLocation();
    final String previousLocation = ValueUtil.randomString();

    when( callback.onChange( previousLocation, nextLocation ) ).thenReturn( Boolean.TRUE );
    adapter.onChange( previousLocation, nextLocation, control );
    verify( callback ).onChange( previousLocation, nextLocation );
    verify( control ).proceed();
  }

  @Test
  public void onChange_abort()
  {
    final OnChangeControl control = mock( OnChangeControl.class );
    final OnChangeCallback callback = mock( OnChangeCallback.class );
    final OnChangeCallbackAsyncAdapter adapter = new OnChangeCallbackAsyncAdapter( callback );

    final Location nextLocation = FactoryUtil.createLocation();
    final String previousLocation = ValueUtil.randomString();

    when( callback.onChange( previousLocation, nextLocation ) ).thenReturn( Boolean.FALSE );
    adapter.onChange( previousLocation, nextLocation, control );
    verify( callback ).onChange( previousLocation, nextLocation );
    verify( control ).abort();
  }
}
