package router.client;

/**
 * This callback is passed into transition callback and is invoked to indicate that transition is complete.
 */
@FunctionalInterface
public interface PostRouteCallback
{
  void postRoute( Route route );
}
