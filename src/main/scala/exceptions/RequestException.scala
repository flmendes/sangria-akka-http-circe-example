package exceptions

case class RequestException(message: String) extends BaseException(message,"request")