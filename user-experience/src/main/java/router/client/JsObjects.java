package router.client;

public class JsObjects
{
  public static native <T> T get( Object object, String propertyName ) /*-{
    return object[ propertyName ];
  }-*/;

  public static native void set( Object object, String propertyName, Object value ) /*-{
    object[ propertyName ] = value;
  }-*/;

  private JsObjects()
  {
  }
}
