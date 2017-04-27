package router.client.api2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@SuppressWarnings( "NonJREEmulationClassesInClientCode" )
public final class OnChangeCallbackChainTest
{
  @Test
  public void emptyChain_goesToNext()
  {
    final ArrayList<RouteEntry<OnChangeCallbackAsync>> elements = new ArrayList<>();
    final OnChangeCallbackChain chain = new OnChangeCallbackChain( elements );

    final Runnable abortAction = mock( Runnable.class );
    final Runnable nextAction = mock( Runnable.class );

    final String previousLocation = ValueUtil.randomString();

    chain.onChange( previousLocation, abortAction, nextAction );

    verify( abortAction, never() ).run();
    verify( nextAction ).run();
  }

  @Test
  public void basicChain()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnChangeCallbackAsync>> elements = new ArrayList<>();
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    final OnChangeCallbackChain chain = new OnChangeCallbackChain( elements );

    final Runnable abortAction = mock( Runnable.class );
    final Runnable nextAction = mock( Runnable.class );

    chain.onChange( ValueUtil.randomString(), abortAction, nextAction );

    verify( abortAction, never() ).run();
    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Test
  public void basicChainWithHalt()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnChangeCallbackAsync>> elements = new ArrayList<>();
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::halt ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    final OnChangeCallbackChain chain = new OnChangeCallbackChain( elements );

    final Runnable abortAction = mock( Runnable.class );
    final Runnable nextAction = mock( Runnable.class );

    chain.onChange( ValueUtil.randomString(), abortAction, nextAction );

    verify( abortAction, never() ).run();
    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Test
  public void basicChainWithAbort()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnChangeCallbackAsync>> elements = new ArrayList<>();
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, OnChangeControl::abort ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    final OnChangeCallbackChain chain = new OnChangeCallbackChain( elements );

    final Runnable abortAction = mock( Runnable.class );
    final Runnable nextAction = mock( Runnable.class );

    chain.onChange( ValueUtil.randomString(), abortAction, nextAction );

    verify( abortAction ).run();
    verify( nextAction, never() ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Test
  public void basicChainAbortAfterHalt()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnChangeCallbackAsync>> elements = new ArrayList<>();
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::proceed ) );
    elements.add( newEntry( count, ChainControl::halt ) );
    elements.add( newEntry( count, OnChangeControl::abort ) );
    final OnChangeCallbackChain chain = new OnChangeCallbackChain( elements );

    final Runnable abortAction = mock( Runnable.class );
    final Runnable nextAction = mock( Runnable.class );

    chain.onChange( ValueUtil.randomString(), abortAction, nextAction );

    verify( abortAction, never() ).run();
    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Nonnull
  private RouteEntry<OnChangeCallbackAsync> newEntry( @Nonnull final AtomicInteger count,
                                                      @Nonnull final Consumer<OnChangeControl> callback )
  {
    return new RouteEntry<>( FactoryUtil.createLocation(), ( p, l, c ) ->
    {
      count.incrementAndGet();
      callback.accept( c );
    } );
  }
}
