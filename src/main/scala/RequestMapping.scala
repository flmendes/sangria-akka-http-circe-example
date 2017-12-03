import handlers.{JsonHandler, SangriaHandler}

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

trait RequestMapping { _: JsonHandler with SangriaHandler =>

  def asRoute: Route  = jsonEndpoint ~ sangriaEndpoint

}
