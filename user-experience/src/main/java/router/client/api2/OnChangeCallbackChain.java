package router.client.api2;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class OnChangeCallbackChain
{
  @Nonnull
  private final List<RouteEntry<OnChangeCallbackAsync>> _elements;

  public OnChangeCallbackChain( @Nonnull final List<RouteEntry<OnChangeCallbackAsync>> elements )
  {
    _elements = Objects.requireNonNull( elements );
  }

  public void onChange( @Nonnull final RouteContext context,
                        @Nullable final String previousLocation,
                        @Nonnull final Runnable abortAction,
                        @Nonnull final Runnable nextAction )
  {
    onChange( context, previousLocation, abortAction, nextAction, 0 );
  }

  private void onChange( @Nonnull final RouteContext context,
                         @Nullable final String previousLocation,
                         @Nonnull final Runnable abortAction,
                         @Nonnull final Runnable nextAction,
                         final int index )
  {
    if ( index >= _elements.size() )
    {
      nextAction.run();
    }
    else
    {
      final RouteEntry<OnChangeCallbackAsync> entry = _elements.get( index );
      entry.getCallback().onChange( context, previousLocation, entry.getRoute(), new OnChangeControl()
      {
        @Override
        public void abort()
        {
          abortAction.run();
        }

        @Override
        public void halt()
        {
          nextAction.run();
        }

        @Override
        public void proceed()
        {
          onChange( context, previousLocation, abortAction, nextAction, index + 1 );
        }
      } );
    }
  }
}
