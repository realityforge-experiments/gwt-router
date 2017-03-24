package router.client.api2;

import javax.annotation.Nonnull;
import router.client.Route;

class PostRouteCallbackAsyncAdapter
  implements PostRouteCallbackAsync
{
  private final PostRouteCallback _callback;

  @SuppressWarnings( "ConstantConditions" )
  PostRouteCallbackAsyncAdapter( @Nonnull final PostRouteCallback callback )
  {
    assert null != callback;
    _callback = callback;
  }

  @Override
  public void postRoute( @Nonnull final Route route, @Nonnull final PostRouteControl control )
  {
    _callback.postRoute( route );
    control.continueRouting();
  }
}
