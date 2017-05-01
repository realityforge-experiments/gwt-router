package router.client.location;

import java.util.Map;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class LocationPatternTest
{
  @DataProvider( name = "pathToRegex" )
  public static Object[][] pathToRegexData()
  {
    return new Object[][]{
      { "X", "^X$" },
      { "/abc/1!", "^\\/abc\\/1!$" },
      { "/+$#-?()[]{}.*\\", "^\\/\\+\\$#\\-\\?\\(\\)\\[\\]\\{\\}\\.\\*\\\\$" },
      };
  }

  @Test( dataProvider = "pathToRegex" )
  public void pathToPattern( @Nonnull final String path, @Nonnull final String expected )
    throws Exception
  {
    assertEquals( LocationPattern.pathToPattern( path ), expected );
  }

  @Test
  public void match()
  {
    final String location = ValueUtil.randomString();
    final String[] results = { location, "a", "b", "c" };
    final String[] parameterKeys = { "param0", null, "param2", "param3" };
    final LocationPattern pattern = new LocationPattern( new TestRegExp( results ), parameterKeys );
    final LocationMatch match = pattern.match( location );
    assertNotNull( match );
    assertEquals( match.getLocation(), location );
    assertEquals( match.getPattern(), pattern );
    final Map<String, Object> matchData = match.getParameters();
    assertEquals( matchData.size(), 3 );
    assertEquals( matchData.get( "param0" ), "a" );
    assertEquals( matchData.get( "p1" ), "b" );
    assertEquals( matchData.get( "param2" ), "c" );
  }

  @Test
  public void match_whereGuardRejectsMatch()
  {
    final String location = ValueUtil.randomString();
    final String[] results = { location, "a", "b", "c" };
    final String[] parameterKeys = { "param0", null, "param2", "param3" };
    final LocationPattern.GuardCallback guard = mock( LocationPattern.GuardCallback.class );
    final LocationPattern pattern = new LocationPattern( new TestRegExp( results ), parameterKeys, guard );
    when( guard.shouldMatch( eq( location ), eq( pattern ), anyMapOf( String.class, Object.class ) ) ).
      thenReturn( false );
    assertNull( pattern.match( location ) );

    verify( guard ).shouldMatch( eq( location ), eq( pattern ), anyMapOf( String.class, Object.class ) );
  }

  @Test
  public void match_whereGuardModifiesData()
  {
    final String location = ValueUtil.randomString();
    final String[] results = { location, "1" };
    final String[] parameterKeys = { "param0" };
    final LocationPattern.GuardCallback guard = ( location1, pattern1, matchData ) ->
    {
      matchData.put( "param0", Integer.parseInt( (String) matchData.get( "param0" ) ) );
      return true;
    };
    final LocationPattern pattern = new LocationPattern( new TestRegExp( results ), parameterKeys, guard );
    final LocationMatch match = pattern.match( location );
    assertNotNull( match );
    assertEquals( match.getLocation(), location );
    assertEquals( match.getPattern(), pattern );
    final Map<String, Object> matchData = match.getParameters();
    assertEquals( matchData.size(), 1 );
    assertEquals( matchData.get( "param0" ), 1 );
  }

  @Test
  public void match_withNoGroupsInMatch()
  {
    final String location = ValueUtil.randomString();
    final String[] results = { location };
    final String[] parameterKeys = {};
    final LocationPattern pattern = new LocationPattern( new TestRegExp( results ), parameterKeys );
    final LocationMatch match = pattern.match( location );
    assertNotNull( match );
    assertEquals( match.getLocation(), location );
    assertEquals( match.getPattern(), pattern );
    final Map<String, Object> matchData = match.getParameters();
    assertNotNull( matchData );
    assertEquals( matchData.size(), 0 );
  }

  @Test
  public void match_noMatch()
  {
    final LocationPattern pattern = new LocationPattern( new TestRegExp(), new String[]{} );
    assertNull( pattern.match( ValueUtil.randomString() ) );
  }

  @Test
  public void toKey()
  {
    final String[] parameterKeys = { "a", null, "c" };
    final LocationPattern pattern = new LocationPattern( new TestRegExp(), parameterKeys );
    assertEquals( pattern.toKey( 0 ), "a" );
    assertEquals( pattern.toKey( 1 ), "p1" );
    assertEquals( pattern.toKey( 2 ), "c" );
    assertEquals( pattern.toKey( 3 ), "p3" );
  }
}
