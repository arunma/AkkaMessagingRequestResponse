package me.rerun.akkanotes.messaging.requestresponse

import scala.util.Random

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.actorRef2Scala
import me.rerun.akkanotes.messaging.protocols.TeacherProtocol._

/*
 * The Philosophy Teacher
 * 
 */

class TeacherActor extends Actor with ActorLogging {

  val quotes = List(
    "Moderation is for cowards",
    "Anything worth doing is worth overdoing",
    "The trouble is you think you have time",
    "You never gonna know if you never even try")

  def receive = {

    case QuoteRequest => {

      import util.Random

      //Get a random Quote from the list and construct a response
      val quoteResponse = QuoteResponse(quotes(Random.nextInt(quotes.size)))

      log.info ("QuoteRequest received. Sending response to Student")
      //respond back to the Student who is the original sender of QuoteRequest
      sender ! quoteResponse

    }

  }

}