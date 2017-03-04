package router.client;

/**
 * This callback is used to perform actual routing.
 */
@FunctionalInterface
public interface RouteCallback
{
  void route( Route route, Object element );
}
