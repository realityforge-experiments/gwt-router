package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface OnLeaveCallbackAsync
{
  void onLeave( @Nonnull Route route, @Nonnull OnLeaveControl control );
}
