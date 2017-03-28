package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;
import router.client.Route;

public final class RouteEntry<T>
{
  private final Route _route;
  private final T _callback;

  public RouteEntry( @Nonnull final Route route, @Nonnull final T callback )
  {
    _route = Objects.requireNonNull( route );
    _callback = Objects.requireNonNull( callback );
  }

  @Nonnull
  public Route getRoute()
  {
    return _route;
  }

  public T getCallback()
  {
    return _callback;
  }
}
