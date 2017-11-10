package router.client.api2;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;

public class TestContext
  implements RouteContext
{
  @Nonnull
  @Override
  public String getLocation()
  {
    return ValueUtil.randomString();
  }

  @Nonnull
  @Override
  public Map<String, Object> getParameters()
  {
    return new HashMap<>();
  }
}
