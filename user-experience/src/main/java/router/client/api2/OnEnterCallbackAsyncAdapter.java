package router.client.api2;

import javax.annotation.Nonnull;
import router.client.Route;

final class OnEnterCallbackAsyncAdapter
  implements OnEnterCallbackAsync
{
  private final OnEnterCallback _callback;

  @SuppressWarnings( "ConstantConditions" )
  OnEnterCallbackAsyncAdapter( @Nonnull final OnEnterCallback callback )
  {
    assert null != callback;
    _callback = callback;
  }

  @Override
  public void onEnter( @Nonnull final Route nextRoute, @Nonnull final OnEnterControl control )
  {
    _callback.onEnter( nextRoute );
    control.continueProcessing();
  }
}
