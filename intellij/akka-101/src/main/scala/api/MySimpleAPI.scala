package api
import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.HttpApp

import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.concurrent.duration._
import scala.io.StdIn
import scala.util.{Failure, Success}
object MySimpleAPI  {

  val content =
    """
      |<html>
      | <head></head>
      | <body>
      |   This is an HTML page served by Akka HTTP!
      | </body>
      |</html>
""".stripMargin


  val route = get {
    complete(
      HttpEntity(
        ContentTypes.`text/html(UTF-8)`,
        content
      )
    )
  }
  implicit val system = ActorSystem("MyServer")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  def main(args: Array[String]): Unit = {
    val host = "0.0.0.0"
    val port = sys.env.getOrElse("PORT", "9090").toInt
    val f = for { bindingFuture <- Http().bindAndHandle(route, host, port)
                  waitOnFuture <- Promise[Done].future}  yield waitOnFuture
    println(s"Server online at http://localhost:8080/")

    sys.addShutdownHook {
      // cleanup logic
      println(s"Server shutdown")
    }
    Await.ready(f, Duration.Inf)



  }
}
