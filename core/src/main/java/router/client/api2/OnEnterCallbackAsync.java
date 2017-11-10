package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;

@JsFunction
@FunctionalInterface
public interface OnEnterCallbackAsync
{
  void onEnter( @Nonnull RouteContext context, @Nonnull Route route, @Nonnull OnEnterControl control );
}
