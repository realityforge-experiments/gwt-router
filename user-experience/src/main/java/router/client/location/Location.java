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
  private final Map<String, Object> _parameters;

  public Location( @Nonnull final String location,
                   @Nonnull final LocationDefinition definition,
                   @Nonnull final Map<String, Object> parameters )
  {
    _location = Objects.requireNonNull( location );
    _definition = Objects.requireNonNull( definition );
    _parameters = Objects.requireNonNull( parameters );
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
  public <T> T getParameter( @Nonnull final String key )
  {
    return (T) getParameters().get( key );
  }

  @Nonnull
  public Map<String, Object> getParameters()
  {
    return _parameters;
  }
}
