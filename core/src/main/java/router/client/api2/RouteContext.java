package router.client.api2;

import java.util.Map;
import javax.annotation.Nonnull;

/**
 * Route context contains all the state that has been collected by traversing the callbacks.
 */
public interface RouteContext
{
  @Nonnull
  String getLocation();

  @Nonnull
  Map<String, Object> getParameters();
}
