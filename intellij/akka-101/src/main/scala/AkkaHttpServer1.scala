


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn

object AkkaHttpServer1 {

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
  def main(args: Array[String]): Unit = {
    implicit  val system = ActorSystem("MyServer")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher
    val host = "0.0.0.0"
    val port = sys.env.getOrElse("PORT","9090").toInt
    val bindingFuture = Http().bindAndHandle(route,host,port)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate())

  }
}
