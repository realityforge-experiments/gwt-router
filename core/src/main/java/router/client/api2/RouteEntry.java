package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;

final class RouteEntry<T>
{
  private final Route _route;
  private final T _callback;

  RouteEntry( @Nonnull final Route route, @Nonnull final T callback )
  {
    _route = Objects.requireNonNull( route );
    _callback = Objects.requireNonNull( callback );
  }

  @Nonnull
  Route getRoute()
  {
    return _route;
  }

  T getCallback()
  {
    return _callback;
  }
}
