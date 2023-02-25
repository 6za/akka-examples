package api

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._

import scala.concurrent.duration._
import scala.concurrent.{Await, Promise}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

sealed trait Message

case class Bid(userId: String, offer: Int) extends Message

object AppRouter {
  val controller = new TestController()
  implicit val bidFormat: RootJsonFormat[Bid] = jsonFormat2(Bid.apply)
  val route = post {
    path("test") {
      entity(as[Bid]) { order =>
        complete(
          {
            controller.process(order)
          }
        )
      }
    }
  }
}
object PostAPI {
  implicit val bidFormat: RootJsonFormat[Bid] = jsonFormat2(Bid.apply)
  implicit val system = ActorSystem("MyServer")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher
  def main(args: Array[String]): Unit = {
    val host = "0.0.0.0"
    val port = sys.env.getOrElse("PORT", "9090").toInt
    val f = for { bindingFuture <- Http().bindAndHandle(AppRouter.route, host, port)
                  waitOnFuture <- Promise[Done].future}  yield waitOnFuture
    println(s"Server online at http://localhost:8080/")

    sys.addShutdownHook {
      // cleanup logic
      println(s"Server shutdown")
    }
    Await.ready(f, Duration.Inf)
  }
}

class TestController {
  implicit val bidFormat: RootJsonFormat[Bid] = jsonFormat2(Bid.apply)
  def process(input :Bid): ToResponseMarshallable = {
    println(s"received request")
    println(s"received request" + input)
    input
  }
}

