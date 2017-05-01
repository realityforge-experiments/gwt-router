package router.client.api2;

import java.util.Objects;
import javax.annotation.Nonnull;
import router.client.location.LocationMatch;

final class OnEnterCallbackAsyncAdapter
  implements OnEnterCallbackAsync
{
  private final OnEnterCallback _callback;

  OnEnterCallbackAsyncAdapter( @Nonnull final OnEnterCallback callback )
  {
    _callback = Objects.requireNonNull( callback );
  }

  @Override
  public void onEnter( @Nonnull final LocationMatch match, @Nonnull final OnEnterControl control )
  {
    _callback.onEnter( match );
    control.proceed();
  }
}
