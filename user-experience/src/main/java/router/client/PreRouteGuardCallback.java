package router.client;

/**
 * This callback is called to determine whether the application can begin transitioning to a new location.
 */
@FunctionalInterface
public interface PreRouteGuardCallback
{
  boolean preRouteGuard( Route route );
}
