import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import com.typesafe.config.{Config, ConfigFactory}
import handlers.{JsonHandler, SangriaHandler}

import scala.concurrent.ExecutionContext

class App extends Infrastructure
  with JsonHandler
  with SangriaHandler
  with RequestMapping
  with BenchmarkBootstrap
{

  implicit lazy val system: ActorSystem = ActorSystem("akka-http-benchmark")
  lazy val executionContext: ExecutionContext = system.dispatcher
  lazy val materializer: Materializer = ActorMaterializer()
  lazy val config: Config = ConfigFactory.load

}
