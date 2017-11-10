package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;

@JsFunction
@FunctionalInterface
public interface OnLeaveCallback
{
  void onLeave( @Nonnull RouteContext context, @Nonnull Route route );

  default OnLeaveCallbackAsync asAsync()
  {
    return new OnLeaveCallbackAsyncAdapter( this );
  }
}
