package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.location.LocationMatch;
import router.client.location.Route;

@JsFunction
@FunctionalInterface
public interface OnEnterCallback
{
  void onEnter( @Nonnull RouteContext context, @Nonnull Route route );

  default OnEnterCallbackAsync asAsync()
  {
    return new OnEnterCallbackAsyncAdapter( this );
  }
}
