package router.client.api2;

import javax.annotation.Nonnull;
import router.client.Route;

final class PreRouteCallbackAsyncAdapter
  implements PreRouteCallbackAsync
{
  private final PreRouteCallback _callback;

  @SuppressWarnings( "ConstantConditions" )
  PreRouteCallbackAsyncAdapter( @Nonnull final PreRouteCallback callback )
  {
    assert null != callback;
    _callback = callback;
  }

  @Override
  public void preRoute( @Nonnull final Route route, @Nonnull final PreRouteControl control )
  {
    if ( _callback.preRoute( route ) )
    {
      control.continueRouting();
    }
    else
    {
      control.abortRouting();
    }
  }
}
