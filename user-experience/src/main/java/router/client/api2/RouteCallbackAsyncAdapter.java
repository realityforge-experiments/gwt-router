package router.client.api2;

import javax.annotation.Nonnull;
import router.client.Route;

final class RouteCallbackAsyncAdapter
  implements RouteCallbackAsync
{
  private final RouteCallback _callback;

  @SuppressWarnings( "ConstantConditions" )
  RouteCallbackAsyncAdapter( @Nonnull final RouteCallback callback )
  {
    assert null != callback;
    _callback = callback;
  }

  @Override
  public void route( @Nonnull final Route previousRoute,
                     @Nonnull final Route nextRoute,
                     @Nonnull final RouteControl control )
  {
    _callback.route( previousRoute, nextRoute );
    control.continueRouting();
  }
}
