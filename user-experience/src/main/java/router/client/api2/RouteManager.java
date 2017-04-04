package router.client.api2;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RouteManager
{
  @Nonnull
  private OnChangeCallbackChain collectOnChangeCallbacks( @Nonnull String location )
  {
    return new OnChangeCallbackChain( new ArrayList<>() );
  }

  @Nonnull
  private OnEnterCallbackChain collectOnEnterCallbacks( @Nonnull String location )
  {
    return new OnEnterCallbackChain( new ArrayList<>() );
  }

  @Nonnull
  private OnLeaveCallbackChain collectOnLeaveCallbacks( @Nullable String location )
  {
    return new OnLeaveCallbackChain( new ArrayList<>() );
  }

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
