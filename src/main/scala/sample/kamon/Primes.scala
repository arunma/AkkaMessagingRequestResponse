package sample.kamon

import akka.actor.Actor
import RandomNumberActor.PrimeFactors

class Primes extends Actor {

  def receive = {
    case i: Long => sender ! PrimeFactors(primes(i))
    case i: Int  => sender ! PrimeFactors(primes(i))
  }

  /**
   * Shameless copy
   * @see http://technologyconversations.com/2014/01/15/scala-tutorial-through-katas-prime-factors-easy/
   */
  def primes(number: Long, list: List[Long] = Nil): List[Long] = {
    for (n <- 2L to number if (number % n == 0)) {
      return primes(number / n, list :+ n)
    }
    list
  }

}