package router.client.api2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.annotations.JsFunction;
import router.client.location.LocationMatch;
import router.client.location.Route;

@JsFunction
@FunctionalInterface
public interface OnChangeCallbackAsync
{
  void onChange( @Nonnull RouteContext context, @Nullable String previousLocation, @Nonnull Route route, @Nonnull OnChangeControl control );
}
