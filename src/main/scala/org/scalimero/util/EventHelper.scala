package org.scalimero.util

import scala.collection.immutable.Map

trait EventHelper[T] {
  var callbacks = Map[Any, List[() => Unit]]()
  
}
