package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface RouteCallbackAsync
{
  void route( @Nonnull Route previousRoute, @Nonnull Route nextRoute, @Nonnull RouteControl control );
}
