package router.client.api2.backend;

import javax.annotation.Nullable;

/**
 * Interface via which different backends can be implemented.
 */
public interface RoutingBackend
{
  @FunctionalInterface
  interface HashChangeListener
  {
    boolean onHashChange( @Nullable String hash );
  }
  /**
   * Return the hash indicating the
   */
  @Nullable
  String getHash();

  void setHash( @Nullable String hash );

  Object addListener( HashChangeListener handler );

  void removeListener( Object handle );
}
