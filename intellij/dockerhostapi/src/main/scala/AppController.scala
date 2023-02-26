package tech.code
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat
import akka.http.scaladsl.marshalling.ToResponseMarshallable

class AppController {
  implicit val bidFormat: RootJsonFormat[DockerCompose] = jsonFormat2(DockerCompose.apply)

  def process(input: DockerCompose): ToResponseMarshallable = {
    println(s"received request")
    println(s"received request" + input)
    input
  }
}
