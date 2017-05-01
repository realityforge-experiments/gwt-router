package router.client.api2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import router.client.location.Location;
import router.client.location.LocationDefinition;
import router.client.location.LocationPattern;
import router.client.location.TestRegExp;

final class FactoryUtil
{
  private FactoryUtil()
  {
  }

  @Nonnull
  static Location createLocation()
  {
    final String newLocation = ValueUtil.randomString();
    final TestRegExp regExp = new TestRegExp();
    final LocationPattern pattern = new LocationPattern( regExp, new String[ 0 ] );
    final LocationDefinition definition = new LocationDefinition( pattern, null );
    return new Location( newLocation, definition, Collections.emptyMap() );
  }

  @Nonnull
  static RouteEntry<OnLeaveCallbackAsync> createOnLeaveCallbackAsync( @Nonnull final AtomicInteger count,
                                                                      @Nonnull final Consumer<OnLeaveControl> callback )
  {
    return new RouteEntry<>( createLocation(), ( l, c ) ->
    {
      count.incrementAndGet();
      callback.accept( c );
    } );
  }

  @Nonnull
  static RouteEntry<OnEnterCallbackAsync> createOnEnterCallbackAsync( @Nonnull final AtomicInteger count,
                                                                      @Nonnull final Consumer<OnEnterControl> callback )
  {
    return new RouteEntry<>( createLocation(), ( l, c ) ->
    {
      count.incrementAndGet();
      callback.accept( c );
    } );
  }

  @Nonnull
  static RouteEntry<OnChangeCallbackAsync> createOnChangeCallbackAsync( @Nonnull final AtomicInteger count,
                                                                        @Nonnull final Consumer<OnChangeControl> callback )
  {
    return new RouteEntry<>( createLocation(), ( p, l, c ) ->
    {
      count.incrementAndGet();
      callback.accept( c );
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
