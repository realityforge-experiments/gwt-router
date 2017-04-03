package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.route.Route;

@JsFunction
@FunctionalInterface
public interface OnEnterCallback
{
  void onEnter( @Nonnull Route nextRoute );

  default OnEnterCallbackAsync asAsync()
  {
    return new OnEnterCallbackAsyncAdapter( this );
  }
}
