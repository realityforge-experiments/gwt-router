package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface PostRouteCallbackAsync
{
  void postRoute( @Nonnull Route route, @Nonnull PostRouteControl control );
}
