import akka.actor.typed.ActorSystem

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.typed.scaladsl.Behaviors

object AsynchSynch {
  // synch, block
  def blockingFunction(x: Int): Int = {
    Thread.sleep(10000)
    x + 42
  }
  blockingFunction(5)
  val meaningOfLife = 42

  //requires the import: import scala.concurrent.ExecutionContext.Implicits.global
  // to load the needed implicits

  // asynch, block
  def asyncBlockingFunction(x: Int): Future[Int] = Future {
    Thread.sleep(10000)
    x + 42
  }
  asyncBlockingFunction(5)
  val anotherMeaningOfLife = 43 //evaluates immediately





  def main(args: Array[String]): Unit = {

  }
}

object AsynchSynchSimpleActor {
  // asynch, non-block
  // lambda = Behavior
  def createSimpleActor() = Behaviors.receiveMessage[String] { someMessage =>
    println(s"Received a message: $someMessage")
    Behaviors.same
  }
  val rootActor = ActorSystem(createSimpleActor(), "TestSystem")
  rootActor ! "Message in a bottle" // async and non-blocking
  def main(args: Array[String]): Unit = {

  }
}

object AsynchSynchActor {
  val promiseResolver = ActorSystem(
    Behaviors.receiveMessage[(String, Promise[Int])] {
      case (message, promise) =>
        // do some calculation
        promise.success(message.length)
        Behaviors.same
    }, "promiseResolver"
  )
  def doAsyncNonBlockingComputation(s: String): Future[Int] = {
    val aPromise = Promise[Int]()
    promiseResolver ! (s, aPromise)
    aPromise.future
  }
  val asyscNonBlockingResult = doAsyncNonBlockingComputation("Some message")
  asyscNonBlockingResult.onComplete(println)
  def main(args: Array[String]): Unit = {
  }
}