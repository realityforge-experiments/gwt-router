package router.client.api2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
  public void onChange( @Nullable String previousLocation,
                        @Nonnull Route nextRoute,
                        @Nonnull final OnChangeControl control )
  {
    if ( _callback.onChange( previousLocation, nextRoute ) )
    {
      control.continueProcessing();
    }
    else
    {
      control.abortChange();
    }
  }
}
