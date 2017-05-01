package router.client.location;

import javax.annotation.Nonnull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
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
