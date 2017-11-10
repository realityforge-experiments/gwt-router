package router.client.api2;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class TestContext
  implements RouteContext
{
  @Nonnull
  @Override
  public Map<String, Object> getParameters()
  {
    return new HashMap<>();
  }
}
