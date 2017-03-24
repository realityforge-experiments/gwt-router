package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface PreRouteCallbackAsync
{
  void preRoute( @Nonnull Route route, @Nonnull PreRouteControl control );
}
