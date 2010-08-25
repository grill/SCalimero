import org.scalimero.dsl._

object Example extends Application {

//the knx router towards our network is at 172.19.0.7, and it should immediately be opened
Network("172.19.0.7") open

//define our lamps
val lA = Lamp("1/1/0")
val lB = Lamp("1/1/1")

//turn lamp A on and off
lA turn on
lA turn off

//subscribe to lamp A's on event, i.e. lB gets turned on whenever lA is turned on
lA.eventSubscribe(on){
  lB turn on
}
}
