package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.location.LocationMatch;

@JsFunction
@FunctionalInterface
public interface OnEnterCallback
{
  void onEnter( @Nonnull LocationMatch match );

  default OnEnterCallbackAsync asAsync()
  {
    return new OnEnterCallbackAsyncAdapter( this );
  }
}
