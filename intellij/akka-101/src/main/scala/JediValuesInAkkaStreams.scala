import akka.Done
import akka.actor.TypedActor.dispatcher
import akka.actor.{Actor, ActorSystem}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import scala.concurrent.Future

//https://www.youtube.com/watch?v=2-CK76cPB9s&list=PLmtsMNDRU0Bz3zS-3wqY4Z5tNBz6nft1W&index=2
object JediValuesInAkkaStreams {
  //Actor System
  implicit val system = ActorSystem()

  // Some Sort of thread pool
  import system.dispatcher

  val source = Source(1 to 1000)
  val flow = Flow[Int].map(x => x * 2)
  val sink = Sink.foreach[Int](println)

  val graph = source.via(flow).to(sink)

  val anotherGraph = source.via(flow).toMat(sink)((leftJediValue, rightJediValue) => rightJediValue)
  def main(args: Array[String]): Unit = {
    val jediValue = graph.run()
    val anotherJediValue: Future[Done] = anotherGraph.run()

    //Allows to monitor the stream execution
    anotherJediValue.onComplete(_ => println("Stream done"))
  }
}

object JediValuesInAkkaStreamsSummingSink {
  //Actor System
  implicit val system = ActorSystem()

  // Some Sort of thread pool
  import system.dispatcher

  val source = Source(1 to 1000)
  val summingSink = Sink.fold[Int, Int](0)((currentSum, incomingElement) => currentSum + incomingElement)

  def main(args: Array[String]): Unit = {
    val sumFuture: Future[Int] = source.toMat(summingSink)(Keep.right).run()
    sumFuture.foreach(println)
    //Jedi Values  = Materialized Values
  }
}