package router.client.route;

/**
 * This callback is called before a transition occurs to a new location and before any action is taken by
 * the route manager. When the completion callback is invoked, the route manager will u
 */
@FunctionalInterface
public interface BeforeRouteCallback
{
  void beforeRoute( Route route );
}
