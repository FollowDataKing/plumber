package org.plumber.api

/**
 * Created by baihe on 16/4/15.
 */
class Drip(val raw: Any) {
  private lazy val hasAttrs = raw.isInstanceOf[Map[String, _]]
  private val attrs: Map[String, _] = if (hasAttrs) raw.asInstanceOf[Map[String, _]] else Map()

  def hasSchema() = hasAttrs && attrs.size > 0
}

object Drip {
  implicit def drip2Attrs(drip: Drip): Map[String, _] = drip.attrs

  def apply(raw: Any) = new Drip(raw)
}

//class Drip[A] private (val raw: A, val hasAttrs : Boolean) {
//  private val attrs: Map[String, _] = if (hasAttrs) raw.asInstanceOf[Map[String, _]] else Map()
//
//  def hasSchema() = hasAttrs && attrs.size > 0
//}
//
//object Drip {
//  implicit def drip2Attrs(drip: Drip[_]): Map[String, _] = drip.attrs
//
////  def apply[A](raw: A)(implicit evidence$1: TypeTag[A]) = new Drip[A](raw, typeOf[A] <:< typeOf[Map[String, Any]])
//  def apply[A](raw: A) = new Drip[A](raw, raw.isInstanceOf[Map[String, _]])
//}


