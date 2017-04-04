package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.location.Location;

@JsFunction
@FunctionalInterface
public interface OnLeaveCallback
{
  void onLeave( @Nonnull Location location );

  default OnLeaveCallbackAsync asAsync()
  {
    return new OnLeaveCallbackAsyncAdapter( this );
  }
}
