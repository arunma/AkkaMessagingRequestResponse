package me.rerun.akkanotes.messaging.protocols

/*
 * It's just nice to have a object wrap all your `messages` in a 
 * nice little object - Purely for management
 * 
 * Messages are predominantly case classes because of all the goodies that 
 * come along with it
 */

object StudentProtocol{
  
  /*
   * the Driver App issues this signal to ask the 
   * Student actor to send message to Teacher Actor
   *  
   */
  case class InitSignal()
  
  
}