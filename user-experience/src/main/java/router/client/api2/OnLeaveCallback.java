package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface OnLeaveCallback
{
  void onLeave( @Nonnull Route route );

  default OnLeaveCallbackAsync asAsync()
  {
    return new OnLeaveCallbackAsyncAdapter( this );
  }
}
