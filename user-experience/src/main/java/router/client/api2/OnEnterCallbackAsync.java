package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface OnEnterCallbackAsync
{
  void onEnter( @Nonnull Route previousRoute, @Nonnull Route nextRoute, @Nonnull OnEnterControl control );
}
