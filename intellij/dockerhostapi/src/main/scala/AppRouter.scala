package tech.code
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat
import akka.http.scaladsl.server.Directives._

object AppRouter {
  val controller = new AppController()
  implicit val dockerComposeFormat: RootJsonFormat[DockerCompose] = jsonFormat2(DockerCompose.apply)
  val route = post {
    path("test") {
      entity(as[DockerCompose]) { order =>
        complete(
          {
            controller.process(order)
          }
        )
      }
    }
  }
}