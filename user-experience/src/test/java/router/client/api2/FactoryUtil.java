package router.client.api2;

import java.util.Collections;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import router.client.location.Location;
import router.client.location.LocationDefinition;
import router.client.location.LocationPattern;
import router.client.location.TestRegExp;

final class FactoryUtil
{
  private FactoryUtil()
  {
  }

  @Nonnull
  static Location createLocation()
  {
    final String newLocation = ValueUtil.randomString();
    final TestRegExp regExp = new TestRegExp( newLocation );
    final LocationPattern pattern = new LocationPattern( regExp, new String[ 0 ] );
    final LocationDefinition definition = new LocationDefinition( pattern, null );
    return new Location( newLocation, definition, Collections.emptyMap() );
  }
}
