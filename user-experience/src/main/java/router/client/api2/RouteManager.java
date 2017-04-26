package router.client.api2;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RouteManager
  extends AbstractRouteManager
{
  @Nonnull
  @Override
  protected OnChangeCallbackChain collectOnChangeCallbacks( @Nonnull String location )
  {
    return new OnChangeCallbackChain( new ArrayList<>() );
  }

  @Nonnull
  @Override
  protected OnEnterCallbackChain collectOnEnterCallbacks( @Nonnull String location )
  {
    return new OnEnterCallbackChain( new ArrayList<>() );
  }

  @Nonnull
  @Override
  protected OnLeaveCallbackChain collectOnLeaveCallbacks( @Nullable String location )
  {
    return new OnLeaveCallbackChain( new ArrayList<>() );
  }
}
