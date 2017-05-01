package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;
import router.client.location.LocationMatch;

public final class RouteEntry<T>
{
  private final LocationMatch _match;
  private final T _callback;

  public RouteEntry( @Nonnull final LocationMatch match, @Nonnull final T callback )
  {
    _match = Objects.requireNonNull( match );
    _callback = Objects.requireNonNull( callback );
  }

  @Nonnull
  public LocationMatch getMatch()
  {
    return _match;
  }

  public T getCallback()
  {
    return _callback;
  }
}
