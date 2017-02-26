package router.client;

/**
 * This callback is passed into transition callback and is invoked to indicate that transition is complete.
 */
@FunctionalInterface
public interface ContinueRoutingCallback
{
  void continueRouting();
}
