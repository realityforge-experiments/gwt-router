package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.location.Location;

@JsFunction
@FunctionalInterface
public interface OnEnterCallbackAsync
{
  void onEnter( @Nonnull Location nextLocation, @Nonnull OnEnterControl control );
}
