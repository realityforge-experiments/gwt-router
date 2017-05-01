package router.client.location;

import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

public class LocationDefinitionTest
{
  @Test
  public void basicOperation()
  {
    final LocationPattern pattern1 = new LocationPattern( new TestRegExp(), new String[ 0 ] );
    final LocationPattern pattern2 = new LocationPattern( new TestRegExp(), new String[ 0 ] );

    final LocationDefinition definition1 = new LocationDefinition( pattern1 );
    assertEquals( definition1.getPattern(), pattern1 );
    assertEquals( definition1.getLocationGuard(), null );

    final LocationGuardCallback guard = mock( LocationGuardCallback.class );
    final LocationDefinition definition2 = new LocationDefinition( pattern2, guard );
    assertEquals( definition2.getPattern(), pattern2 );
    assertEquals( definition2.getLocationGuard(), guard );
  }
}
