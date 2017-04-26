package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;
import router.client.location.Location;

class OnLeaveCallbackAsyncAdapter
  implements OnLeaveCallbackAsync
{
  private final OnLeaveCallback _callback;

  OnLeaveCallbackAsyncAdapter( @Nonnull final OnLeaveCallback callback )
  {
    _callback = Objects.requireNonNull( callback );
  }

  @Override
  public void onLeave( @Nonnull final Location location, @Nonnull final OnLeaveControl control )
  {
    _callback.onLeave( location );
    control.proceed();
  }
}
