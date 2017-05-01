package router.client.location;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class LocationMatch
{
  @Nonnull
  private final String _location;
  @Nonnull
  private final LocationPattern _pattern;
  @Nonnull
  private final Map<String, Object> _parameters;

  public LocationMatch( @Nonnull final String location,
                        @Nonnull final LocationPattern pattern,
                        @Nonnull final Map<String, Object> parameters )
  {
    _location = Objects.requireNonNull( location );
    _pattern = Objects.requireNonNull( pattern );
    _parameters = Collections.unmodifiableMap( Objects.requireNonNull( parameters ) );
  }

  @Nonnull
  public String getLocation()
  {
    return _location;
  }

  @Nonnull
  public LocationPattern getPattern()
  {
    return _pattern;
  }

  @Nonnull
  public Map<String, Object> getParameters()
  {
    return _parameters;
  }
}
