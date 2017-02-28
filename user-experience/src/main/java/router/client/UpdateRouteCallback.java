package router.client;

import elemental2.Element;

/**
 * This callback is used if the definition is the same but parameters have changed.
 */
@FunctionalInterface
public interface UpdateRouteCallback
{
  void updateRoute( Route route );
}
