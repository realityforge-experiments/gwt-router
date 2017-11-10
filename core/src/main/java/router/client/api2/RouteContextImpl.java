package router.client.api2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

public class RouteContextImpl
  implements RouteContext
{
  private final String _location;
  private final Map<String, Object> _parameters = new HashMap<>();

  public RouteContextImpl( @Nonnull final String location )
  {
    _location = Objects.requireNonNull( location );
  }

  @Nonnull
  @Override
  public String getLocation()
  {
    return _location;
  }

  @Nonnull
  @Override
  public Map<String, Object> getParameters()
  {
    return _parameters;
  }
}
