package me.rerun.akkanotes.messaging.requestresponse

import org.scalatest.BeforeAndAfterAll
import org.scalatest.MustMatchers
import org.scalatest.WordSpecLike
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.testkit.ImplicitSender
import akka.testkit.TestKit
import me.rerun.akkanotes.messaging.protocols.StudentProtocol.InitSignal
import me.rerun.akkanotes.messaging.protocols.TeacherProtocol.QuoteRequest
import me.rerun.akkanotes.messaging.protocols.TeacherProtocol.QuoteResponse
import akka.event.EventStream
import akka.testkit.EventFilter
import me.rerun.akkanotes.messaging.requestresponse.StudentActor
import akka.util.Timeout


/**
 * This Test case exactly does what the StudentActor was doing in the
 * requestresponse package
 *
 */
class RequestResponseTest extends TestKit(ActorSystem("TestUniversityMessageSystem", ConfigFactory.parseString("""
                                            akka{
                                              loggers = ["akka.testkit.TestEventListener"]
                                              test{
                                                  filter-leeway = 7s
                                              }
                                            }
                                    """)))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll 
  with ImplicitSender {

  "A teacher" must {

    "respond with a QuoteResponse when a QuoteRequest message is sent" in {

      val teacherRef = system.actorOf(Props[TeacherActor], "teacherActorChild")
      teacherRef!QuoteRequest

      //expectMsg(QuoteResponse(_)) //Asserting that we are expecting a message back
      expectMsgPF() {

        case QuoteResponse(quoteResponse) => println(quoteResponse)
        case _ => fail("Quote response not received")

      }
      

    }

  }
  
 "A student" must {

    "log a QuoteResponse eventually when an InitSignal is sent to it" in {

      import me.rerun.akkanotes.messaging.protocols.StudentProtocol._
      
      val teacherRef = system.actorOf(Props[TeacherActor], "teacherActor")
      
      val studentRef = system.actorOf(Props(new StudentActor(teacherRef)), "studentActor")
      
      EventFilter.info (start="Printing from Student Actor", occurrences=1).intercept{
        studentRef!InitSignal
      }
      
    }

  }
 
 "A delayed student" must {

    "fire the QuoteRequest after 5 seconds when an InitSignal is sent to it" in {

      import me.rerun.akkanotes.messaging.protocols.StudentProtocol._
      
      val teacherRef = system.actorOf(Props[TeacherActor], "teacherActorDelayed")
      val studentRef = system.actorOf(Props(new StudentDelayedActor(teacherRef)), "studentDelayedActor")
      
      EventFilter.info (start="Printing from Student Actor", occurrences=1).intercept{
          studentRef!InitSignal
      }
      
    }

  }
 
 
 
  override def afterAll() {
    super.afterAll()
    system.shutdown()

  }

}