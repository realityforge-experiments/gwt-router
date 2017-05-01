package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.location.LocationMatch;

@JsFunction
@FunctionalInterface
public interface OnLeaveCallback
{
  void onLeave( @Nonnull LocationMatch match );

  default OnLeaveCallbackAsync asAsync()
  {
    return new OnLeaveCallbackAsyncAdapter( this );
  }
}
