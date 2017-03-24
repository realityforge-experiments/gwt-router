package router.client.api2;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import router.client.Route;

@JsFunction
@FunctionalInterface
public interface PreRouteCallback
{
  boolean preRoute( @Nonnull Route route );

  default PreRouteCallbackAsync asAsync()
  {
    return new PreRouteCallbackAsyncAdapter( this );
  }
}
