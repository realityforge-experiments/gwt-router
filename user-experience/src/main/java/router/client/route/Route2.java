package router.client.route;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.client.api2.RouteContext;

public final class Route2
{
  @Nonnull
  private final RouteContext _context;
  @Nonnull
  private final RouteDefinition _definition;

  public Route2( @Nonnull final RouteContext match, @Nonnull final RouteDefinition definition )
  {
    _context = Objects.requireNonNull( match );
    _definition = Objects.requireNonNull( definition );
  }

  @Nonnull
  public RouteContext getContext()
  {
    return _context;
  }

  @Nonnull
  public RouteDefinition getDefinition()
  {
    return _definition;
  }

  @SuppressWarnings( "unchecked" )
  @Nullable
  public <T> T getData( @Nonnull final String key )
  {
    return (T) getContext().getParameters().get( key );
  }
}
