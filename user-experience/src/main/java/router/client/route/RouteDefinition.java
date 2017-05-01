package router.client.route;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.client.api2.Route;

public final class RouteDefinition
{
  @Nonnull
  private final Route _location;
  @Nullable
  private final BeforeRouteCallback _beforeRoute;
  @Nonnull
  private final RouteCallback _route;
  @Nullable
  private final UpdateRouteCallback _updateRoute;

  public RouteDefinition( @Nonnull final Route location,
                          @Nullable final BeforeRouteCallback beforeRoute,
                          @Nonnull final RouteCallback route,
                          @Nullable final UpdateRouteCallback updateRoute )
  {
    _location = Objects.requireNonNull( location );
    _beforeRoute = beforeRoute;
    _route = Objects.requireNonNull( route );
    _updateRoute = updateRoute;
  }

  @Nonnull
  public Route getLocation()
  {
    return _location;
  }

  @Nullable
  public BeforeRouteCallback getBeforeRoute()
  {
    return _beforeRoute;
  }

  @Nonnull
  public RouteCallback getRoute()
  {
    return _route;
  }

  @Nullable
  public UpdateRouteCallback getUpdateRoute()
  {
    return _updateRoute;
  }
}
