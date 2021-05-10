package sns

import org.http4s._

enum Action:
  case CreateTopic
  case ListTopics
  case DeleteTopic

object Action:
  given QueryParamDecoder[Action] = QueryParamDecoder[String].map(Action.valueOf)