package router.client.api2;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class OnEnterCallbackChain
{
  @Nonnull
  private final List<RouteEntry<OnEnterCallbackAsync>> _elements;

  public OnEnterCallbackChain( @Nonnull final List<RouteEntry<OnEnterCallbackAsync>> elements )
  {
    _elements = Objects.requireNonNull( elements );
  }

  public void onEnter( @Nonnull final RouteContext context, @Nonnull final Runnable nextAction )
  {
    onEnter( context, nextAction, 0 );
  }

  private void onEnter( @Nonnull final RouteContext context, @Nonnull final Runnable nextAction, final int index )
  {
    if ( index >= _elements.size() )
    {
      nextAction.run();
    }
    else
    {
      final RouteEntry<OnEnterCallbackAsync> entry = _elements.get( index );
      entry.getCallback().onEnter( context, entry.getRoute(), new OnEnterControl()
      {
        @Override
        public void proceed()
        {
          onEnter( context, nextAction, index + 1 );
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
