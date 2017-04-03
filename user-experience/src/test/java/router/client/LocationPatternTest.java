package router.client;

import javax.annotation.Nonnull;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import router.client.location.LocationPattern;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@PrepareForTest( { LocationPattern.class, RegExp.class } )
public class LocationPatternTest
{
  @ObjectFactory
  public IObjectFactory getObjectFactory()
  {
    return new PowerMockObjectFactory();
  }

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
  public void pathToRegex( @Nonnull final String path, @Nonnull final String regex )
    throws Exception
  {
    final RegExp mock = PowerMockito.mock( RegExp.class );
    PowerMockito.whenNew( RegExp.class ).withArguments( eq( regex ) ).thenReturn( mock );

    final RegExp re = LocationPattern.pathToRegex( path );

    assertEquals( re, mock );

    PowerMockito.verifyNew( RegExp.class ).withArguments( eq( regex ) );
  }
}
