package router.client.api2;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class OnChangeCallbackChain
{
  @Nonnull
  private final List<RouteEntry<OnChangeCallbackAsync>> _chain;

  public OnChangeCallbackChain( @Nonnull final List<RouteEntry<OnChangeCallbackAsync>> chain )
  {
    _chain = Objects.requireNonNull( chain );
  }

  public void onChange( @Nullable final String previousLocation,
                        @Nonnull final Runnable abortRoute,
                        @Nonnull final Runnable continueRoute )
  {
    onChange( previousLocation, abortRoute, continueRoute, 0 );
  }

  private void onChange( @Nullable final String previousLocation,
                         @Nonnull final Runnable abortRoute,
                         @Nonnull final Runnable continueRoute,
                         final int index )
  {
    if ( index >= _chain.size() )
    {
      continueRoute.run();
    }
    else
    {
      final RouteEntry<OnChangeCallbackAsync> entry = _chain.get( index );
      entry.getCallback().onChange( previousLocation, entry.getLocation(), new OnChangeControl()
      {
        @Override
        public void abort()
        {
          abortRoute.run();
        }

        @Override
        public void proceed()
        {
          onChange( previousLocation, abortRoute, continueRoute, index + 1 );
        }
      } );
    }
  }
}
