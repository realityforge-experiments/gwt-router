package router.client.api2;

public interface ChainControl
{
  /**
   * The callback chain should proceed to the next step.
   */
  void proceed();

  /**
   * The callback chain should be terminated. No more steps should be progressed.
   */
  void halt();
}
