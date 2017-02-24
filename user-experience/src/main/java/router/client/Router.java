package router.client;

import com.google.gwt.core.client.EntryPoint;
import elemental2.Global;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Router
  implements EntryPoint
{
  private static final Logger LOG = Logger.getLogger( Router.class.getName() );

  public void onModuleLoad()
  {
    try
    {
      LOG.info( "Router started" );
    }
    catch ( final Exception e )
    {
      LOG.log( Level.SEVERE, "Unexpected problem initializing the Router application", e );
      Global.window.alert( "Error: " + e.getMessage() );
    }
  }
}
