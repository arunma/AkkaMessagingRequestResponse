package me.rerun.akkanotes.messaging.requestresponse

import akka.actor.Actor
import akka.actor.ActorLogging
import me.rerun.akkanotes.messaging.protocols.TeacherProtocol._
import me.rerun.akkanotes.messaging.protocols.StudentProtocol._
import akka.actor.Props
import akka.actor.ActorRef
import scala.concurrent.duration._

/*
 * The Student Actor that uses Scheduler. 
 * 
 */

class StudentDelayedActor (teacherActorRef:ActorRef) extends Actor with ActorLogging {

  def receive = {

    /*
     * This InitSignal is received from the DriverApp. 
     * On receipt and after 5 seconds, the Student sends a message to the Teacher actor. 
     * The teacher actor on receipt of the QuoteRequest responds with a QuoteResponse 
     */
    case InitSignal=> {
      import context.dispatcher
      //context.system.scheduler.scheduleOnce(5 seconds, teacherActorRef, QuoteRequest)
      context.system.scheduler.schedule(0 seconds, 5 seconds, teacherActorRef, QuoteRequest)
      //teacherActorRef!QuoteRequest
    }
    
    case QuoteResponse(quoteString) => {
      log.info ("Received QuoteResponse from Teacher")
      log.info(s"Printing from Student Actor $quoteString")
    }

  }

}