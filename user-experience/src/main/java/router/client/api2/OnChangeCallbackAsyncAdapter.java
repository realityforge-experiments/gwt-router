package router.client.api2;

import javax.annotation.Nonnull;
import router.client.Route;

final class OnChangeCallbackAsyncAdapter
  implements OnChangeCallbackAsync
{
  private final OnChangeCallback _callback;

  @SuppressWarnings( "ConstantConditions" )
  OnChangeCallbackAsyncAdapter( @Nonnull final OnChangeCallback callback )
  {
    assert null != callback;
    _callback = callback;
  }

  @Override
  public void onChange( @Nonnull final Route previousRoute,
                        @Nonnull Route nextRoute,
                        @Nonnull final OnChangeControl control )
  {
    if ( _callback.onChange( previousRoute, nextRoute ) )
    {
      control.continueProcessing();
    }
    else
    {
      control.abortChange();
    }
  }
}
