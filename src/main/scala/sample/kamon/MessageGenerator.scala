package sample.kamon

import akka.actor._
import scala.concurrent.duration._
import scala.math.random
import MessageGenerator._

/**
 * Defines different ways to create artifical traffic
 */
class MessageGeneratorActor extends Actor {

  val maxPeak = 10000
  val constantLoad = 500

  var peak: ActorRef = _
  var load: ActorRef = _

  override def preStart() {
    peak = context.actorOf(Props[PeakGenerator], "peak:generator")
    load = context.actorOf(Props[ConstantLoadGenerator], "load:generator")
  }

  def receive = {
    case Peak(s)         => peak ! s
    case ConstantLoad(s) => load ! s
  }
}

/**
 * Generating messages
 */
trait MessageGenerator extends Actor with ActorLogging {

  override def receive = {
    case Schedule(target, msg, times) => schedule(target, msg, times)
    case msg                          => log warning s"unkown: $msg"
  }

  def schedule(target: ActorRef, message: Any, times: Int)

}

/** the protocol */
object MessageGenerator {

  case class Peak[A](schedule: A)
  case class ConstantLoad(schedule: Schedule)

  case class Schedule(target: ActorRef, msg: Any, times: Int)

}

/**
 * Generates peaks in random intervals
 * @param numMessages - how many messages should be in a peak
 */
class PeakGenerator extends MessageGenerator {

  import context._

  def schedule(target: ActorRef, message: Any, numMessages: Int) {
    log info s"schedule peak $message"
    val wait = (random * 10.0).toLong
    system.scheduler.scheduleOnce(wait seconds, self, Schedule(target, message, numMessages))
    for (i <- 0 to numMessages) {
      target ! message
    }
  }
}

/**
 * Generates a constant message load
 * @param msgPerSecond - how many messages should be sent per second
 */
class ConstantLoadGenerator extends MessageGenerator {

  import context._

  def schedule(target: ActorRef, message: Any, msgPerSecond: Int) {
    system.scheduler.schedule(
      initialDelay = 0 seconds,
      interval = (1000 / msgPerSecond) milliseconds,
      receiver = target,
      message = message
    )
  }
}