package handlers

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._

import exceptions.{QueryException, RequestException}
import io.circe.Json
import io.circe.parser._
import io.circe.generic.auto._
import sangria.{CharacterRepo, SchemaDefinition}
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.execution.deferred.DeferredResolver
import sangria.marshalling.circe._
import sangria.parser.QueryParser


import scala.util.{Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global

case class GraphQLInput(query: String, operation: Option[String], variables: Option[Json])

trait SangriaHandler {

  import marshaller.CirceSupport._

  def sangriaEndpoint =
    (post & path("graphql")) {
      entity(as[Json]) { requestJson =>

        val inputXor = requestJson.as[GraphQLInput]

        val input = inputXor.getOrElse(throw new RequestException(s"Input request is not valid : ${inputXor.swap.map(_.getMessage()).getOrElse("")}"))

        val vars = input.variables.map(v => {
          v match {
            case o: Json if (o.isObject) => o
            case s: Json if (s.isString) => parse(s.asString.get).getOrElse(Json.obj())
            case _ => Json.obj()
          }
        }).getOrElse(Json.obj())

        QueryParser.parse(input.query) match {
          case Success(queryAst) =>
            complete(Executor.execute(SchemaDefinition.StarWarsSchema, queryAst, new CharacterRepo,
              variables = vars,
              operationName = input.operation,
              deferredResolver = DeferredResolver.fetchers(SchemaDefinition.characters))
              .map(OK -> _)
              .recover {
                case error: QueryAnalysisError => BadRequest -> error.resolveError
                case error: ErrorWithResolver => InternalServerError -> error.resolveError
              })
          case Failure(error) => complete(BadRequest, Json.obj(("error", Json.fromString(error.getMessage))))
        }
      }

    } ~
    (get & path("graphql")) {
        getFromResource("graphiql.html")
    }

}
