package router.client.api2;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class OnLeaveCallbackChain
{
  @Nonnull
  private final List<RouteEntry<OnLeaveCallbackAsync>> _elements;

  public OnLeaveCallbackChain( @Nonnull final List<RouteEntry<OnLeaveCallbackAsync>> elements )
  {
    _elements = Objects.requireNonNull( elements );
  }

  public void onLeave( @Nonnull final RouteContext context, @Nonnull final Runnable nextAction )
  {
    onLeave( context, nextAction, 0 );
  }

  private void onLeave( @Nonnull final RouteContext context, @Nonnull final Runnable nextAction, final int index )
  {
    if ( index >= _elements.size() )
    {
      nextAction.run();
    }
    else
    {
      final RouteEntry<OnLeaveCallbackAsync> entry = _elements.get( index );
      entry.getCallback().onLeave( context, entry.getRoute(), new OnLeaveControl()
      {
        @Override
        public void proceed()
        {
          onLeave( context, nextAction, index + 1 );
        }

        @Override
        public void halt()
        {
          nextAction.run();
        }
      } );
    }
  }
}
