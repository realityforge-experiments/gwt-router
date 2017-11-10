package router.client.api2;

import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

@SuppressWarnings( "deprecation" )
public class RouteBuilderTest
{
  @Test
  public void minimalBuild()
  {
    final TestRegExp matcher = new TestRegExp();
    final RouteBuilder b = new RouteBuilder( matcher );

    assertEquals( b.getMatcher(), matcher );
    assertEquals( b.getGuard(), null );
    assertEquals( b.getOnChange(), null );
    assertEquals( b.getOnEnter(), null );
    assertEquals( b.getOnLeave(), null );
    assertEquals( b.getParameterKeys(), null );
    assertEquals( b.getChildren().size(), 0 );

    final Route r = b.build();
    assertEquals( r.getMatcher(), matcher );
    assertEquals( r.getGuard(), null );
    assertEquals( r.getOnChange(), null );
    assertEquals( r.getOnEnter(), null );
    assertEquals( r.getOnLeave(), null );
    assertEquals( r.getParameterKeys(), null );
    assertEquals( r.getChildren().size(), 0 );
  }

  @Test
  public void fullBuild()
  {
    final TestRegExp matcher = new TestRegExp();
    final Route.GuardCallback guard = mock( Route.GuardCallback.class );
    final OnChangeCallbackAsync onChange = mock( OnChangeCallbackAsync.class );
    final OnEnterCallbackAsync onEnter = mock( OnEnterCallbackAsync.class );
    final OnLeaveCallbackAsync onLeave = mock( OnLeaveCallbackAsync.class );
    final String[] parameterKeys = new String[ 1 ];
    final RouteBuilder b =
      new RouteBuilder( matcher ).
        setGuard( guard ).
        setOnChange( onChange ).
        setOnEnter( onEnter ).
        setOnLeave( onLeave ).
        setParameterKeys( parameterKeys );

    assertEquals( b.getMatcher(), matcher );
    assertEquals( b.getGuard(), guard );
    assertEquals( b.getOnChange(), onChange );
    assertEquals( b.getOnEnter(), onEnter );
    assertEquals( b.getOnLeave(), onLeave );
    assertEquals( b.getParameterKeys(), parameterKeys );
    assertEquals( b.getChildren().size(), 0 );

    final Route r = b.build();
    assertEquals( r.getMatcher(), matcher );
    assertEquals( r.getGuard(), guard );
    assertEquals( r.getOnChange(), onChange );
    assertEquals( r.getOnEnter(), onEnter );
    assertEquals( r.getOnLeave(), onLeave );
    assertEquals( r.getParameterKeys(), parameterKeys );
    assertEquals( r.getChildren().size(), 0 );
  }
}
