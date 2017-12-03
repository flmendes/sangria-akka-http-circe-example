package exceptions

case class QueryException(message: String) extends BaseException(message,"query")