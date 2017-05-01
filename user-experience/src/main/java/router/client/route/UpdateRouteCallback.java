package router.client.route;

/**
 * This callback is used if the definition is the same but parameters have changed.
 */
@FunctionalInterface
public interface UpdateRouteCallback
{
  void updateRoute( Route2 route );
}
