package router.client.api2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import router.client.location.Route;
import router.client.location.TestRegExp;

final class FactoryUtil
{
  private FactoryUtil()
  {
  }

  @Nonnull
  static Route createRoute()
  {
    final TestRegExp regExp = new TestRegExp();
    return new Route( regExp, new String[ 0 ] );
  }

  @Nonnull
  static RouteEntry<OnLeaveCallbackAsync> createOnLeaveCallbackAsync( @Nonnull final AtomicInteger count,
                                                                      @Nonnull final Consumer<OnLeaveControl> callback )
  {
    return new RouteEntry<>( createRoute(), ( c, r, cb ) ->
    {
      count.incrementAndGet();
      callback.accept( cb );
    } );
  }

  @Nonnull
  static RouteEntry<OnEnterCallbackAsync> createOnEnterCallbackAsync( @Nonnull final AtomicInteger count,
                                                                      @Nonnull final Consumer<OnEnterControl> callback )
  {
    return new RouteEntry<>( createRoute(), ( c, r, cb ) ->
    {
      count.incrementAndGet();
      callback.accept( cb );
    } );
  }

  @Nonnull
  static RouteEntry<OnChangeCallbackAsync> createOnChangeCallbackAsync( @Nonnull final AtomicInteger count,
                                                                        @Nonnull final Consumer<OnChangeControl> callback )
  {
    return new RouteEntry<>( createRoute(), ( c, p, r, cb ) ->
    {
      count.incrementAndGet();
      callback.accept( cb );
    } );
  }

  @SafeVarargs
  @Nonnull
  static OnChangeCallbackChain createOnChangeCallbackChain( @Nonnull final AtomicInteger count,
                                                            @Nonnull final Consumer<OnChangeControl>... actions )
  {
    final List<RouteEntry<OnChangeCallbackAsync>> elements = new ArrayList<>();
    for ( final Consumer<OnChangeControl> action : actions )
    {
      elements.add( createOnChangeCallbackAsync( count, action ) );
    }
    return new OnChangeCallbackChain( elements );
  }

  @SafeVarargs
  @Nonnull
  static OnEnterCallbackChain createOnEnterCallbackChain( @Nonnull final AtomicInteger count,
                                                          @Nonnull final Consumer<OnEnterControl>... actions )
  {
    final List<RouteEntry<OnEnterCallbackAsync>> elements = new ArrayList<>();
    for ( final Consumer<OnEnterControl> action : actions )
    {
      elements.add( createOnEnterCallbackAsync( count, action ) );
    }
    return new OnEnterCallbackChain( elements );
  }

  @SafeVarargs
  @Nonnull
  static OnLeaveCallbackChain createOnLeaveCallbackChain( @Nonnull final AtomicInteger count,
                                                          @Nonnull final Consumer<OnLeaveControl>... actions )
  {
    final List<RouteEntry<OnLeaveCallbackAsync>> elements = new ArrayList<>();
    for ( final Consumer<OnLeaveControl> action : actions )
    {
      elements.add( createOnLeaveCallbackAsync( count, action ) );
    }
    return new OnLeaveCallbackChain( elements );
  }
}
