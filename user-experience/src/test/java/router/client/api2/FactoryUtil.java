package router.client.api2;

import java.util.Collections;
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
    final TestRegExp regExp = new TestRegExp( newLocation );
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
}
