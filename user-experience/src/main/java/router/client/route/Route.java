package router.client.route;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.client.location.LocationMatch;

public final class Route
{
  @Nonnull
  private final LocationMatch _match;
  @Nonnull
  private final RouteDefinition _definition;

  public Route( @Nonnull final LocationMatch match, @Nonnull final RouteDefinition definition )
  {
    assert Objects.equals( match.getPattern(), definition.getLocation() );
    _match = Objects.requireNonNull( match );
    _definition = Objects.requireNonNull( definition );
  }

  @Nonnull
  public LocationMatch getMatch()
  {
    return _match;
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
    return (T) getMatch().getParameters().get( key );
  }
}
