package router.client.api2;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class OnLeaveCallbackChain
{
  @Nonnull
  private final List<RouteEntry<OnLeaveCallbackAsync>> _chain;

  public OnLeaveCallbackChain( @Nonnull final List<RouteEntry<OnLeaveCallbackAsync>> chain )
  {
    _chain = Objects.requireNonNull( chain );
  }

  public void onLeave( @Nonnull final Runnable nextAction )
  {
    onLeave( nextAction, 0 );
  }

  private void onLeave( @Nonnull final Runnable nextAction, final int index )
  {
    if ( index >= _chain.size() )
    {
      nextAction.run();
    }
    else
    {
      final RouteEntry<OnLeaveCallbackAsync> entry = _chain.get( index );
      entry.getCallback().onLeave( entry.getRoute(), new OnLeaveControl()
      {
        @Override
        public void continueProcessing()
        {
          onLeave( nextAction, index + 1 );
        }
      } );
    }
  }
}
