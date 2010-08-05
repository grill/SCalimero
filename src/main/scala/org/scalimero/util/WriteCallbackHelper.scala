package org.scalimero.util

trait WriteCallbackHelper[T] {
  class WriteCallback(var fun : T => Unit) {
    def apply(arg : T) = fun(arg)
    def update(newFun : T => Unit) = fun = newFun
    def detach = unsubscribe(this)
  }
  var callbacks = List[WriteCallback]()
  
  def subscribe(callback : T => Unit) = {
    val wcallback = new WriteCallback(callback)
    callbacks = wcallback :: callbacks
    wcallback
  }
  def unsubscribe(callback : WriteCallback) {
    callbacks = callbacks filterNot (_ == callback)
  }
  def callWrites(value : T) {callbacks map {_(value)}}
}
