package router.client.api2;

public interface OnChangeControl
  extends ChainControl
{
  /**
   * Processing the callback chain should be aborted and the location change should be
   * aborted and the locaiton should revert to previous location.
   */
  void abort();
}
