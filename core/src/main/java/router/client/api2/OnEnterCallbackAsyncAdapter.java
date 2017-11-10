package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;

final class OnEnterCallbackAsyncAdapter
  implements OnEnterCallbackAsync
{
  private final OnEnterCallback _callback;

  OnEnterCallbackAsyncAdapter( @Nonnull final OnEnterCallback callback )
  {
    _callback = Objects.requireNonNull( callback );
  }

  @Override
  public void onEnter( @Nonnull final RouteContext context,
                       @Nonnull final Route route,
                       @Nonnull final OnEnterControl control )
  {
    _callback.onEnter( context, route );
    control.proceed();
  }
}
