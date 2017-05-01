package router.client.api2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractRouteManager
{
  @Nonnull
  protected abstract OnChangeCallbackChain collectOnChangeCallbacks( @Nonnull String location );

  @Nonnull
  protected abstract OnEnterCallbackChain collectOnEnterCallbacks( @Nonnull String location );

  @Nonnull
  protected abstract OnLeaveCallbackChain collectOnLeaveCallbacks( @Nullable String location );

  public void route( @Nullable final String previousLocation,
                     @Nonnull final String newLocation,
                     @Nonnull final RouteContext context,
                     @Nullable final Runnable routingCompleteAction,
                     @Nullable final Runnable routingAbortedAction )
  {
    final Runnable onEnter =
      () -> collectOnEnterCallbacks( newLocation ).onEnter( context, () -> runIfNonNull( routingCompleteAction ) );

    final Runnable onLeave = () -> collectOnLeaveCallbacks( previousLocation ).onLeave( context, onEnter );

    collectOnChangeCallbacks( newLocation ).
      onChange( context, previousLocation, () -> runIfNonNull( routingAbortedAction ), onLeave );
  }

  private void runIfNonNull( @Nullable final Runnable action )
  {
    if ( null != action )
    {
      action.run();
    }
  }
}
