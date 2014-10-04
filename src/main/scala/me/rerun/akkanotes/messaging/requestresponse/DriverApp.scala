package me.rerun.akkanotes.messaging.requestresponse

import akka.actor.ActorSystem
import akka.actor.Props
import me.rerun.akkanotes.messaging.protocols.StudentProtocol._
import akka.actor.ActorRef

object DriverApp extends App{
  
  
  //Initialize the ActorSystem
  val system=ActorSystem("UniversityMessageSystem")
  
  //construct the Student Actor
  val studentRef:ActorRef=system.actorOf(Props[StudentActor], "studentActor")
  
  //send a message to the Student Actor
  studentRef!InitSignal
  
  //Let's wait for a couple of seconds before we shut down the system
  Thread.sleep (2000) 
  
  //Shut down the ActorSystem. 
  system.shutdown()
  

}