package router.client.api2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class AbstractRouteManagerTest
{
  @Test
  public void completedRoute()
  {
    final AtomicInteger onChangeCount = new AtomicInteger();
    final AtomicInteger onEnterCount = new AtomicInteger();
    final AtomicInteger onLeaveCount = new AtomicInteger();
    final OnChangeCallbackChain onChange =
      FactoryUtil.createOnChangeCallbackChain( onChangeCount, ChainControl::proceed, ChainControl::proceed );
    final OnEnterCallbackChain onEnter =
      FactoryUtil.createOnEnterCallbackChain( onEnterCount, ChainControl::proceed, ChainControl::proceed );
    final OnLeaveCallbackChain onLeave =
      FactoryUtil.createOnLeaveCallbackChain( onLeaveCount, ChainControl::proceed, ChainControl::proceed );

    final TestRouteManager rm = new TestRouteManager();
    rm._onChangeCallbackChain = onChange;
    rm._onEnterCallbackChain = onEnter;
    rm._onLeaveCallbackChain = onLeave;

    final TestContext context = new TestContext();
    final Runnable nextAction = mock( Runnable.class );
    final Runnable abortAction = mock( Runnable.class );

    final String previousLocation = ValueUtil.randomString();
    final String newLocation = ValueUtil.randomString();
    rm.route( previousLocation, newLocation, context, nextAction, abortAction );

    assertEquals( rm._onChangeLocation, Boolean.TRUE );
    assertEquals( rm._onEnterLocation, Boolean.TRUE );
    assertEquals( rm._onLeaveLocation, Boolean.TRUE );

    assertEquals( onChangeCount.intValue(), 2 );
    assertEquals( onEnterCount.intValue(), 2 );
    assertEquals( onLeaveCount.intValue(), 2 );

    verify( nextAction ).run();
    verify( abortAction, never() ).run();
  }

  @Test
  public void abortedRoute()
  {
    final AtomicInteger onChangeCount = new AtomicInteger();
    final AtomicInteger onEnterCount = new AtomicInteger();
    final AtomicInteger onLeaveCount = new AtomicInteger();
    final OnChangeCallbackChain onChange =
      FactoryUtil.createOnChangeCallbackChain( onChangeCount, ChainControl::proceed, OnChangeControl::abort );
    final OnEnterCallbackChain onEnter =
      FactoryUtil.createOnEnterCallbackChain( onEnterCount, ChainControl::proceed, ChainControl::proceed );
    final OnLeaveCallbackChain onLeave =
      FactoryUtil.createOnLeaveCallbackChain( onLeaveCount, ChainControl::proceed, ChainControl::proceed );

    final TestRouteManager rm = new TestRouteManager();
    rm._onChangeCallbackChain = onChange;
    rm._onEnterCallbackChain = onEnter;
    rm._onLeaveCallbackChain = onLeave;

    final TestContext context = new TestContext();
    final Runnable nextAction = mock( Runnable.class );
    final Runnable abortAction = mock( Runnable.class );

    final String previousLocation = ValueUtil.randomString();
    final String newLocation = ValueUtil.randomString();
    rm.route( previousLocation, newLocation, context, nextAction, abortAction );

    assertEquals( rm._onChangeLocation, Boolean.TRUE );
    assertNull( rm._onEnterLocation, newLocation );
    assertNull( rm._onLeaveLocation, previousLocation );

    assertEquals( onChangeCount.intValue(), 2 );
    assertEquals( onEnterCount.intValue(), 0 );
    assertEquals( onLeaveCount.intValue(), 0 );

    verify( nextAction, never() ).run();
    verify( abortAction ).run();
  }

  static class TestRouteManager
    extends AbstractRouteManager
  {
    private OnChangeCallbackChain _onChangeCallbackChain;
    private OnEnterCallbackChain _onEnterCallbackChain;
    private OnLeaveCallbackChain _onLeaveCallbackChain;
    private Boolean _onChangeLocation;
    private Boolean _onEnterLocation;
    private Boolean _onLeaveLocation;

    @Nonnull
    @Override
    protected List<Route> getRoutes()
    {
      return new ArrayList<>();
    }

    @Nonnull
    @Override
    protected OnChangeCallbackChain collectOnChangeCallbacks( @Nonnull final List<Route> routes )
    {
      _onChangeLocation = true;
      return _onChangeCallbackChain;
    }

    @Nonnull
    @Override
    protected OnEnterCallbackChain collectOnEnterCallbacks( @Nonnull final List<Route> routes )
    {
      _onEnterLocation = true;
      return _onEnterCallbackChain;
    }

    @Nonnull
    @Override
    protected OnLeaveCallbackChain collectOnLeaveCallbacks( @Nonnull final List<Route> routes )
    {
      _onLeaveLocation = true;
      return _onLeaveCallbackChain;
    }
  }
}
