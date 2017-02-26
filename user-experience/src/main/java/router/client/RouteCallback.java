package router.client;

import elemental2.Element;

/**
 * This callback is used to perform actual routing.
 */
@FunctionalInterface
public interface RouteCallback
{
  void route( Route route, Element element );
}
