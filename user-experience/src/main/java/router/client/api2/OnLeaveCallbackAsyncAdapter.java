package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;
import router.client.location.Route;

class OnLeaveCallbackAsyncAdapter
  implements OnLeaveCallbackAsync
{
  private final OnLeaveCallback _callback;

  OnLeaveCallbackAsyncAdapter( @Nonnull final OnLeaveCallback callback )
  {
    _callback = Objects.requireNonNull( callback );
  }

  @Override
  public void onLeave( @Nonnull final RouteContext context,
                       @Nonnull final Route route,
                       @Nonnull final OnLeaveControl control )
  {
    _callback.onLeave( context, route );
    control.proceed();
  }
}
