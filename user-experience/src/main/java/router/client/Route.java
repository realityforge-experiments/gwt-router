package router.client;

import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Route
{
  @Nonnull
  private final String _location;
  @Nonnull
  private final RouteDefinition _definition;
  @Nullable
  private final Map<String, Object> _routeData;

  public Route( @Nonnull final String location,
                @Nonnull final RouteDefinition definition,
                @Nullable final Map<String, Object> routeData )
  {
    _location = Objects.requireNonNull( location );
    _definition = Objects.requireNonNull( definition );
    _routeData = Objects.requireNonNull( routeData );
  }

  @Nonnull
  public String getLocation()
  {
    return _location;
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
    return null == _routeData ? null : (T) _routeData.get( key );
  }

  @Nullable
  public Map<String, Object> getRouteData()
  {
    return _routeData;
  }
}
