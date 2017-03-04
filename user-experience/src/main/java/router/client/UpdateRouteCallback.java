package router.client;

/**
 * This callback is used if the definition is the same but parameters have changed.
 */
@FunctionalInterface
public interface UpdateRouteCallback
{
  void updateRoute( Route route );
}
