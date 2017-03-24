package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface RouteCallback
{
  void route( @Nonnull Route previousRoute, @Nonnull Route nextRoute );

  default RouteCallbackAsync asAsync()
  {
    return new RouteCallbackAsyncAdapter( this );
  }
}
