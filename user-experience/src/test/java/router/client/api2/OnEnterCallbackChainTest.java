package router.client.api2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@SuppressWarnings( "NonJREEmulationClassesInClientCode" )
public final class OnEnterCallbackChainTest
{
  @Test
  public void emptyChain_goesToNext()
  {
    final ArrayList<RouteEntry<OnEnterCallbackAsync>> elements = new ArrayList<>();
    final OnEnterCallbackChain chain = new OnEnterCallbackChain( elements );

    final Runnable nextAction = mock( Runnable.class );

    chain.onEnter( nextAction );

    verify( nextAction ).run();
  }

  @Test
  public void basicChain()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnEnterCallbackAsync>> elements = new ArrayList<>();
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    final OnEnterCallbackChain chain = new OnEnterCallbackChain( elements );

    final Runnable nextAction = mock( Runnable.class );

    chain.onEnter( nextAction );

    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Test
  public void basicChainWithHalt()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnEnterCallbackAsync>> elements = new ArrayList<>();
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::halt ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    final OnEnterCallbackChain chain = new OnEnterCallbackChain( elements );

    final Runnable nextAction = mock( Runnable.class );

    chain.onEnter( nextAction );

    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Nonnull
  private RouteEntry<OnEnterCallbackAsync> newEntry( @Nonnull final AtomicInteger count,
                                                     @Nonnull final Consumer<OnEnterControl> callback )
  {
    return new RouteEntry<>( FactoryUtil.createLocation(), ( l, c ) ->
    {
      count.incrementAndGet();
      callback.accept( c );
    } );
  }
}
