package router.client.api2;

import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import router.client.location.LocationMatch;
import static org.mockito.Mockito.*;

public final class OnChangeCallbackAsyncAdapterTest
{
  @Test
  public void onChange_proceed()
  {
    final OnChangeControl control = mock( OnChangeControl.class );
    final OnChangeCallback callback = mock( OnChangeCallback.class );
    final OnChangeCallbackAsyncAdapter adapter = new OnChangeCallbackAsyncAdapter( callback );

    final LocationMatch match = FactoryUtil.createLocation();
    final String previousLocation = ValueUtil.randomString();

    when( callback.onChange( previousLocation, match ) ).thenReturn( Boolean.TRUE );
    adapter.onChange( previousLocation, match, control );
    verify( callback ).onChange( previousLocation, match );
    verify( control ).proceed();
  }

  @Test
  public void onChange_abort()
  {
    final OnChangeControl control = mock( OnChangeControl.class );
    final OnChangeCallback callback = mock( OnChangeCallback.class );
    final OnChangeCallbackAsyncAdapter adapter = new OnChangeCallbackAsyncAdapter( callback );

    final LocationMatch match = FactoryUtil.createLocation();
    final String previousLocation = ValueUtil.randomString();

    when( callback.onChange( previousLocation, match ) ).thenReturn( Boolean.FALSE );
    adapter.onChange( previousLocation, match, control );
    verify( callback ).onChange( previousLocation, match );
    verify( control ).abort();
  }
}
