package router.client;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class LocationDefinition
{
  @Nonnull
  private final LocationPattern _pattern;
  @Nullable
  private final LocationGuardCallback _locationGuard;

  public LocationDefinition( @Nonnull final LocationPattern pattern,
                             @Nullable final LocationGuardCallback locationGuard )
  {
    _pattern = Objects.requireNonNull( pattern );
    _locationGuard = locationGuard;
  }

  @Nonnull
  public LocationPattern getPattern()
  {
    return _pattern;
  }

  @Nullable
  public LocationGuardCallback getLocationGuard()
  {
    return _locationGuard;
  }
}
