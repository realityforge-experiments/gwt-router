package router.client.api2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.annotations.JsFunction;
import router.client.route.Route;

@JsFunction
@FunctionalInterface
public interface OnChangeCallbackAsync
{
  void onChange( @Nullable String previousLocation, @Nonnull Route nextRoute, @Nonnull OnChangeControl control );
}
