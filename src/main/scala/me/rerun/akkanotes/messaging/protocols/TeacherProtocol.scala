package me.rerun.akkanotes.messaging.protocols

/*
 * It's just nice to have a object wrap all your `messages` in a 
 * nice little object - Purely for management
 * 
 * Messages are predominantly case classes because of all the goodies that 
 * come along with it
 */

object TeacherProtocol{
  
  /*
   * The Student sends this message to request for a Quotation 
   * 
   */
  case class QuoteRequest()
  
  /* 
   * The TeacherActor responds back to the Student with this message object
   * The actual quote string is wrapped inside the response.
   * 
   */
  case class QuoteResponse(quoteString:String)
  
}