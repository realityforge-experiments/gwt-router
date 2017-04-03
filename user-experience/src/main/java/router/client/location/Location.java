package router.client.location;

import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Location
{
  @Nonnull
  private final String _location;
  @Nonnull
  private final LocationDefinition _definition;
  @Nonnull
  private final Map<String, Object> _routeData;

  public Location( @Nonnull final String location,
                   @Nonnull final LocationDefinition definition,
                   @Nonnull final Map<String, Object> routeData )
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
  public LocationDefinition getDefinition()
  {
    return _definition;
  }

  @SuppressWarnings( "unchecked" )
  @Nullable
  public <T> T getData( @Nonnull final String key )
  {
    return (T) getRouteData().get( key );
  }

  @Nonnull
  public Map<String, Object> getRouteData()
  {
    return _routeData;
  }
}
