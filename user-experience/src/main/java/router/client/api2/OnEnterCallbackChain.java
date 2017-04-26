package router.client.api2;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class OnEnterCallbackChain
{
  @Nonnull
  private final List<RouteEntry<OnEnterCallbackAsync>> _chain;

  public OnEnterCallbackChain( @Nonnull final List<RouteEntry<OnEnterCallbackAsync>> chain )
  {
    _chain = Objects.requireNonNull( chain );
  }

  public void onEnter( @Nonnull final Runnable nextAction )
  {
    onEnter( nextAction, 0 );
  }

  private void onEnter( @Nonnull final Runnable nextAction, final int index )
  {
    if ( index >= _chain.size() )
    {
      nextAction.run();
    }
    else
    {
      final RouteEntry<OnEnterCallbackAsync> entry = _chain.get( index );
      entry.getCallback().onEnter( entry.getLocation(), new OnEnterControl()
      {
        @Override
        public void proceed()
        {
          onEnter( nextAction, index + 1 );
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
