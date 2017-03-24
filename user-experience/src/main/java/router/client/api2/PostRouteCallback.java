package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface PostRouteCallback
{
  void postRoute( @Nonnull Route route );

  default PostRouteCallbackAsync asAsync()
  {
    return new PostRouteCallbackAsyncAdapter( this );
  }
}
