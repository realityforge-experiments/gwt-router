package router.client.api2;

import elemental2.core.RegExp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.anodoc.TestOnly;

public final class RouteBuilder
{
  @Nonnull
  private final RegExp _matcher;
  @Nullable
  private String[] _parameterKeys;
  @Nullable
  private Route.GuardCallback _guard;
  @Nullable
  private OnChangeCallbackAsync _onChange;
  @Nullable
  private OnEnterCallbackAsync _onEnter;
  @Nullable
  private OnLeaveCallbackAsync _onLeave;
  @Nonnull
  private final List<Route> _children = new ArrayList<>();

  public RouteBuilder( @Nonnull final RegExp matcher )
  {
    _matcher = matcher;
  }

  @Nonnull
  public RegExp getMatcher()
  {
    return _matcher;
  }

  @Nullable
  public String[] getParameterKeys()
  {
    return _parameterKeys;
  }

  public RouteBuilder setParameterKeys( @Nullable final String[] parameterKeys )
  {
    _parameterKeys = parameterKeys;
    return this;
  }

  @Nullable
  public Route.GuardCallback getGuard()
  {
    return _guard;
  }

  public RouteBuilder setGuard( @Nullable final Route.GuardCallback guard )
  {
    _guard = guard;
    return this;
  }

  @Nullable
  public OnChangeCallbackAsync getOnChange()
  {
    return _onChange;
  }

  public RouteBuilder setOnChange( @Nullable final OnChangeCallbackAsync onChange )
  {
    _onChange = onChange;
    return this;
  }

  @Nullable
  public OnEnterCallbackAsync getOnEnter()
  {
    return _onEnter;
  }

  public RouteBuilder setOnEnter( @Nullable final OnEnterCallbackAsync onEnter )
  {
    _onEnter = onEnter;
    return this;
  }

  @Nullable
  public OnLeaveCallbackAsync getOnLeave()
  {
    return _onLeave;
  }

  public RouteBuilder setOnLeave( @Nullable final OnLeaveCallbackAsync onLeave )
  {
    _onLeave = onLeave;
    return this;
  }

  public RouteBuilder addChild( @Nonnull final Route route )
  {
    _children.add( route );
    return this;
  }

  @TestOnly
  @Nonnull
  final List<Route> getChildren()
  {
    return _children;
  }

  @Nonnull
  public Route build()
  {
    return new Route( _matcher, _parameterKeys, _guard, _onChange, _onEnter, _onLeave, _children );
  }
}
