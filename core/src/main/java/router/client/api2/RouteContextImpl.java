package router.client.api2;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class RouteContextImpl
  implements RouteContext
{
  private final Map<String, Object> _parameters = new HashMap<>();

  @Nonnull
  @Override
  public Map<String, Object> getParameters()
  {
    return _parameters;
  }
}
