package router.client;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * This class is simple wrapper around javascript RegExp type.
 * It should be compared or potentially replaced by the equivalent in elemental2.
 */
@SuppressWarnings( "unused" )
@JsType( isNative = true, namespace = JsPackage.GLOBAL )
public class RegExp
{
  public RegExp( Object pattern )
  {
  }

  public native String[] exec( Object str );
}
