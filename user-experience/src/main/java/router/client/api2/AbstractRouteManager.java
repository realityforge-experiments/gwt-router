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
                     @Nullable final Runnable routingCompleteAction )
  {
    collectOnChangeCallbacks( newLocation ).
      onChange( previousLocation,
                () -> runIfNonNull( routingCompleteAction ),
                () -> collectOnLeaveCallbacks( previousLocation ).
                  onLeave( () ->
                             collectOnEnterCallbacks( newLocation ).onEnter( () -> runIfNonNull( routingCompleteAction ) ) ) );
  }

  private void runIfNonNull( @Nullable final Runnable action )
  {
    if ( null != action )
    {
      action.run();
    }
  }
}
