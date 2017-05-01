package router.client.location;

public class TestRegExp
  extends RegExp
{
  private String[] _results;

  public TestRegExp()
  {
    super( new Object() );
  }

  public void setResults( final String[] results )
  {
    _results = results;
  }

  @Override
  public String[] exec( final Object str )
  {
    return _results;
  }
}
