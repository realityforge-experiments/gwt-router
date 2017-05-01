package router.client.api2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.annotations.JsFunction;
import router.client.location.LocationMatch;

@JsFunction
@FunctionalInterface
public interface OnChangeCallback
{
  boolean onChange( @Nullable String previousLocation, @Nonnull LocationMatch match );

  default OnChangeCallbackAsync asAsync()
  {
    return new OnChangeCallbackAsyncAdapter( this );
  }
}
