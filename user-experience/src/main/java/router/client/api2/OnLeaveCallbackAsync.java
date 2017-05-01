package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.location.LocationMatch;

@JsFunction
@FunctionalInterface
public interface OnLeaveCallbackAsync
{
  void onLeave( @Nonnull LocationMatch match, @Nonnull OnLeaveControl control );
}
