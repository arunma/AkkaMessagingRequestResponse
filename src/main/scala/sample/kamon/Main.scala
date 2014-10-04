package sample.kamon

import akka.actor._
import MessageGenerator._
import RandomNumberActor._

object Main extends App {

  val system = ActorSystem("application")

  val numberGenerator = system.actorOf(Props[RandomNumberActor], "numbers")

  val generator = system.actorOf(Props[MessageGeneratorActor], "artifical")

  generator ! ConstantLoad(Schedule(numberGenerator, GenerateNumber, 5000))
  generator ! ConstantLoad(Schedule(numberGenerator, GenerateSecureNumber, 1000))
  generator ! Peak(Schedule(numberGenerator, GenerateNumber, 100000))
  generator ! Peak(Schedule(numberGenerator, GenerateSecureNumber, 25000))
}