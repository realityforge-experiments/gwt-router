package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;
import router.client.location.Location;

public final class RouteEntry<T>
{
  private final Location _location;
  private final T _callback;

  public RouteEntry( @Nonnull final Location location, @Nonnull final T callback )
  {
    _location = Objects.requireNonNull( location );
    _callback = Objects.requireNonNull( callback );
  }

  @Nonnull
  public Location getLocation()
  {
    return _location;
  }

  public T getCallback()
  {
    return _callback;
  }
}
