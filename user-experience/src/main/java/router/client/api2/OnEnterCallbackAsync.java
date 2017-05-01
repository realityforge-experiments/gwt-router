package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.location.LocationMatch;

@JsFunction
@FunctionalInterface
public interface OnEnterCallbackAsync
{
  void onEnter( @Nonnull LocationMatch match, @Nonnull OnEnterControl control );
}
