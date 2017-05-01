package router.client.api2;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractRouteManager
{
  public void route( @Nullable final String previousLocation,
                     @Nonnull final String newLocation,
                     @Nonnull final RouteContext context,
                     @Nullable final Runnable routingCompleteAction,
                     @Nullable final Runnable routingAbortedAction )
  {
    final List<Route> routes = getMatchingRoutes( context, newLocation );
    final Runnable onEnter =
      () -> collectOnEnterCallbacks( routes ).onEnter( context, () -> runIfNonNull( routingCompleteAction ) );

    final Runnable onLeave = () -> collectOnLeaveCallbacks( routes ).onLeave( context, onEnter );

    collectOnChangeCallbacks( routes ).
      onChange( context, previousLocation, () -> runIfNonNull( routingAbortedAction ), onLeave );
  }

  @Nonnull
  protected abstract List<Route> getRoutes();

  @SuppressWarnings( "ConstantConditions" )
  @Nonnull
  OnChangeCallbackChain collectOnChangeCallbacks( @Nonnull final List<Route> routes )
  {
    final List<RouteEntry<OnChangeCallbackAsync>> elements = routes.stream().
      filter( r -> null != r.getOnChange() ).
      map( r -> new RouteEntry<>( r, r.getOnChange() ) ).
      collect( Collectors.toList() );
    return new OnChangeCallbackChain( elements );
  }

  @SuppressWarnings( "ConstantConditions" )
  @Nonnull
  OnEnterCallbackChain collectOnEnterCallbacks( @Nonnull final List<Route> routes )
  {
    final List<RouteEntry<OnEnterCallbackAsync>> elements = routes.stream().
      filter( r -> null != r.getOnEnter() ).
      map( r -> new RouteEntry<>( r, r.getOnEnter() ) ).
      collect( Collectors.toList() );
    return new OnEnterCallbackChain( elements );
  }

  @SuppressWarnings( "ConstantConditions" )
  @Nonnull
  OnLeaveCallbackChain collectOnLeaveCallbacks( @Nonnull final List<Route> routes )
  {
    final List<RouteEntry<OnLeaveCallbackAsync>> elements = routes.stream().
      filter( r -> null != r.getOnEnter() ).
      map( r -> new RouteEntry<>( r, r.getOnLeave() ) ).
      collect( Collectors.toList() );
    return new OnLeaveCallbackChain( elements );
  }

  @Nonnull
  private List<Route> getMatchingRoutes( @Nonnull final RouteContext context, @Nonnull final String location )
  {
    return getRoutes().stream().
      filter( r -> r.match( context, location ) ).
      collect( Collectors.toList() );
  }

  private void runIfNonNull( @Nullable final Runnable action )
  {
    if ( null != action )
    {
      action.run();
    }
  }
}
