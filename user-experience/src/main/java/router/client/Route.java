package router.client;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Route
{
  @Nonnull
  private final Location _location;
  @Nonnull
  private final RouteDefinition _definition;

  public Route( @Nonnull final Location location, @Nonnull final RouteDefinition definition )
  {
    assert Objects.equals( location.getDefinition(), definition.getLocation() );
    _location = Objects.requireNonNull( location );
    _definition = Objects.requireNonNull( definition );
  }

  @Nonnull
  public Location getLocation()
  {
    return _location;
  }

  @Nonnull
  public RouteDefinition getDefinition()
  {
    return _definition;
  }

  @Nullable
  public <T> T getData( @Nonnull final String key )
  {
    return getLocation().getData( key );
  }
}
