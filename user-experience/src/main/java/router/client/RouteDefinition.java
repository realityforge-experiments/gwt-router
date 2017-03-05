package router.client;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class RouteDefinition
{
  //The relative priority of the route. Lower priorities are higher priority. Priority should not be below zero.
  @Nonnegative
  private final int _priority;
  @Nonnull
  private final RegExp _matcher;
  @Nullable
  private final String[] _parameterKeys;
  @Nonnull
  private final String _path;
  @Nullable
  private final PreRouteGuardCallback _preRouteGuard;
  @Nullable
  private final BeforeRouteCallback _beforeRoute;
  @Nonnull
  private final RouteCallback _route;
  @Nonnull
  private final UpdateRouteCallback _updateRoute;
  @Nullable
  private final PostRouteCallback _postRoute;

  @SuppressWarnings( "ConstantConditions" )
  public RouteDefinition( @Nonnegative final int priority,
                          @Nonnull final String path,
                          @Nullable final PreRouteGuardCallback preRouteGuard,
                          @Nullable final BeforeRouteCallback beforeRoute,
                          @Nonnull final RouteCallback route,
                          @Nullable final UpdateRouteCallback updateRoute,
                          @Nullable final PostRouteCallback postRoute )
  {
    this( priority,
          path,
          pathToRegex( path ),
          new String[ 0 ],
          preRouteGuard,
          beforeRoute,
          route,
          updateRoute,
          postRoute );
  }

  static RegExp pathToRegex( @Nonnull final String path )
  {
    return new RegExp( "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$" );
  }

  @SuppressWarnings( "ConstantConditions" )
  public RouteDefinition( @Nonnegative final int priority,
                          @Nonnull final RegExp matcher,
                          @Nonnull final String[] parameterKeys,
                          @Nullable final PreRouteGuardCallback preRouteGuard,
                          @Nullable final BeforeRouteCallback beforeRoute,
                          @Nonnull final RouteCallback route,
                          @Nullable final UpdateRouteCallback updateRoute,
                          @Nullable final PostRouteCallback postRoute )
  {
    this( priority,
          matcher.toString(),
          matcher,
          parameterKeys,
          preRouteGuard,
          beforeRoute,
          route,
          updateRoute,
          postRoute );
  }

  @SuppressWarnings( "ConstantConditions" )
  private RouteDefinition( @Nonnegative final int priority,
                           @Nonnull final String path,
                           @Nonnull final RegExp matcher,
                           @Nonnull final String[] parameterKeys,
                           @Nullable final PreRouteGuardCallback preRouteGuard,
                           @Nullable final BeforeRouteCallback beforeRoute,
                           @Nonnull final RouteCallback route,
                           @Nullable final UpdateRouteCallback updateRoute,
                           @Nullable final PostRouteCallback postRoute )
  {
    assert priority >= 0;
    assert null != path;
    assert null != matcher;
    assert null != parameterKeys;
    assert null != route;
    _priority = priority;
    _path = path;
    _parameterKeys = parameterKeys;
    _matcher = matcher;
    _preRouteGuard = preRouteGuard;
    _beforeRoute = beforeRoute;
    _route = route;
    _updateRoute = updateRoute;
    _postRoute = postRoute;
  }

  @Nonnegative
  public int getPriority()
  {
    return _priority;
  }

  @Nonnull
  public String getPath()
  {
    return _path;
  }

  @Nullable
  public PreRouteGuardCallback getPreRouteGuard()
  {
    return _preRouteGuard;
  }

  @Nullable
  public BeforeRouteCallback getBeforeRoute()
  {
    return _beforeRoute;
  }

  @Nonnull
  public RouteCallback getRoute()
  {
    return _route;
  }

  @Nonnull
  public UpdateRouteCallback getUpdateRoute()
  {
    return _updateRoute;
  }

  @Nullable
  public PostRouteCallback getPostRoute()
  {
    return _postRoute;
  }

  @SuppressWarnings( "ConstantConditions" )
  @Nullable
  public Map<String, Object> match( @Nonnull final String location )
  {
    assert null != location;
    final String[] groups = _matcher.exec( location );
    if ( null != groups )
    {
      final HashMap<String, Object> routeData = new HashMap<>();
      //Group 0 is the whole string so we can skip it
      for ( int i = 1; i < groups.length; i++ )
      {
        final String value = groups[ i ];
        final int paramIndex = i - 1;
        final String key = toKey( paramIndex );

        routeData.put( key, value );
      }
      return routeData;
    }
    else
    {
      return null;
    }
  }

  private String toKey( final int paramIndex )
  {
    return _parameterKeys == null || _parameterKeys.length <= paramIndex ?
           "p" + paramIndex :
           _parameterKeys[ paramIndex ];
  }
}
