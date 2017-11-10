package router.client.api2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
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

    final TestContext context = new TestContext();
    final Runnable nextAction = mock( Runnable.class );

    chain.onLeave( context, nextAction );

    verify( nextAction ).run();
  }

  @Test
  public void basicChain()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnLeaveCallbackAsync>> elements = new ArrayList<>();
    elements.add( FactoryUtil.createOnLeaveCallbackAsync( count, ChainControl::proceed ) );
    elements.add( FactoryUtil.createOnLeaveCallbackAsync( count, ChainControl::proceed ) );
    elements.add( FactoryUtil.createOnLeaveCallbackAsync( count, ChainControl::proceed ) );
    final OnLeaveCallbackChain chain = new OnLeaveCallbackChain( elements );

    final TestContext context = new TestContext();
    final Runnable nextAction = mock( Runnable.class );

    chain.onLeave( context, nextAction );

    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }

  @Test
  public void basicChainWithHalt()
  {
    final AtomicInteger count = new AtomicInteger();
    final ArrayList<RouteEntry<OnLeaveCallbackAsync>> elements = new ArrayList<>();
    elements.add( FactoryUtil.createOnLeaveCallbackAsync( count, ChainControl::proceed ) );
    elements.add( FactoryUtil.createOnLeaveCallbackAsync( count, ChainControl::proceed ) );
    elements.add( FactoryUtil.createOnLeaveCallbackAsync( count, ChainControl::halt ) );
    elements.add( FactoryUtil.createOnLeaveCallbackAsync( count, ChainControl::proceed ) );
    final OnLeaveCallbackChain chain = new OnLeaveCallbackChain( elements );

    final TestContext context = new TestContext();
    final Runnable nextAction = mock( Runnable.class );

    chain.onLeave( context, nextAction );

    verify( nextAction ).run();

    assertEquals( count.intValue(), 3 );
  }
}
