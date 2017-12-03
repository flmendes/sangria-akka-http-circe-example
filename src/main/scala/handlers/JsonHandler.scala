package handlers

import akka.http.scaladsl.server.Directives._

case class JsonResponse(message: String)

trait JsonHandler {
  import io.circe.generic.auto._
  import marshaller.CirceSupport._

  def jsonResponse = JsonResponse("Hello, World!") // domain object

  def jsonEndpoint =
    get {
      path("json") {
        complete(jsonResponse)
      }
    }
}
