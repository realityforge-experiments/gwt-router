package router.client.api2;

import javax.annotation.Nonnull;
import router.client.route.Route;

class OnLeaveCallbackAsyncAdapter
  implements OnLeaveCallbackAsync
{
  private final OnLeaveCallback _callback;

  @SuppressWarnings( "ConstantConditions" )
  OnLeaveCallbackAsyncAdapter( @Nonnull final OnLeaveCallback callback )
  {
    assert null != callback;
    _callback = callback;
  }

  @Override
  public void onLeave( @Nonnull final Route route, @Nonnull final OnLeaveControl control )
  {
    _callback.onLeave( route );
    control.continueProcessing();
  }
}
