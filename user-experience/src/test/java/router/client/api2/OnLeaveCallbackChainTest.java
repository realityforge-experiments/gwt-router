package router.client.api2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@SuppressWarnings( "NonJREEmulationClassesInClientCode" )
public final class OnLeaveCallbackChainTest
{
  @Test
  public void emptyChain_goesToNext()
  {
    final ArrayList<RouteEntry<OnLeaveCallbackAsync>> elements = new ArrayList<>();
    final OnLeaveCallbackChain chain = new OnLeaveCallbackChain( elements );

    final Runnable nextAction = mock( Runnable.class );

    chain.onLeave( nextAction );

    verify( nextAction ).run();
  }

  @Test
  public void basicChain()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnLeaveCallbackAsync>> elements = new ArrayList<>();
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    final OnLeaveCallbackChain chain = new OnLeaveCallbackChain( elements );

    final Runnable nextAction = mock( Runnable.class );

    chain.onLeave( nextAction );

    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Test
  public void basicChainWithHalt()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnLeaveCallbackAsync>> elements = new ArrayList<>();
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::halt ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    final OnLeaveCallbackChain chain = new OnLeaveCallbackChain( elements );

    final Runnable nextAction = mock( Runnable.class );

    chain.onLeave( nextAction );

    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Nonnull
  private RouteEntry<OnLeaveCallbackAsync> newEntry( @Nonnull final AtomicInteger count,
                                                     @Nonnull final Consumer<OnLeaveControl> callback )
  {
    return new RouteEntry<>( FactoryUtil.createLocation(), ( l, c ) ->
    {
      count.incrementAndGet();
      callback.accept( c );
    } );
  }
}
