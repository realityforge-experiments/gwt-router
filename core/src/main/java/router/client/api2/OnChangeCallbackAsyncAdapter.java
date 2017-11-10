package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class OnChangeCallbackAsyncAdapter
  implements OnChangeCallbackAsync
{
  private final OnChangeCallback _callback;

  OnChangeCallbackAsyncAdapter( @Nonnull final OnChangeCallback callback )
  {
    _callback = Objects.requireNonNull( callback );
  }

  @Override
  public void onChange( @Nonnull final RouteContext context,
                        @Nullable final String previousLocation,
                        @Nonnull final Route route,
                        @Nonnull final OnChangeControl control )
  {
    if ( _callback.onChange( context, previousLocation, route ) )
    {
      control.proceed();
    }
    else
    {
      control.abort();
    }
  }
}
