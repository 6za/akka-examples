package tech.code

import scala.concurrent.duration._
import scala.concurrent.{Await, Promise}
import akka.http.scaladsl.Http
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat
import akka.actor.ActorSystem
import akka.Done

object Main {
  implicit val bidFormat: RootJsonFormat[DockerCompose] = jsonFormat2(DockerCompose.apply)
  implicit val system = ActorSystem("MyServer")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  def main(args: Array[String]): Unit = {
    val host = "0.0.0.0"
    val port = sys.env.getOrElse("PORT", "9090").toInt
    val f = for {bindingFuture <- Http().bindAndHandle(AppRouter.route, host, port)
                 waitOnFuture <- Promise[Done].future} yield waitOnFuture
    println(s"Server online at http://localhost:8080/")

    sys.addShutdownHook {
      // cleanup logic
      println(s"Server shutdown")
    }
    Await.ready(f, Duration.Inf)
  }
}