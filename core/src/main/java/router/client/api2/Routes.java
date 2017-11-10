package router.client.api2;

import javax.annotation.Nonnull;

public final class Routes
{
  private Routes()
  {
  }

  @Nonnull
  public static RouteBuilder routeFromPath( @Nonnull final String path )
  {
    return route( Route.pathToPattern( path ) );
  }

  @Nonnull
  public static RouteBuilder route( @Nonnull final String pattern )
  {
    return new RouteBuilder( new RegExp( pattern ) );
  }

  @Nonnull
  public static RouteBuilder route( @Nonnull final RegExp matcher )
  {
    return new RouteBuilder( matcher );
  }
}
