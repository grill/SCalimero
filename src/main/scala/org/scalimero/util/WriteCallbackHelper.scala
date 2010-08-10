package org.scalimero.util

trait WriteCallbackHelper[T] {
  class WriteCallback(var fun : T => Unit) {
    def apply(arg : T) = fun(arg)
    def update(newFun : T => Unit) = fun = newFun
    def detach = unsubscribe(this)
  }
  var wcallbacks = List[WriteCallback]()
  
  def subscribe(callback : T => Unit) = {
    val wcallback = new WriteCallback(callback)
    wcallbacks = wcallback :: wcallbacks
    wcallback
  }
  def unsubscribe(callback : WriteCallback) {
    wcallbacks = wcallbacks filterNot (_ == callback)
  }
  def callWrites(value : T) {wcallbacks map {_(value)}}
}
