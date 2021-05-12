package sns

import org.http4s._

import scala.util._

enum Action:
  case CreateTopic
  case ListTopics
  case DeleteTopic
  case Publish
  case Subscribe

object Action:
  given QueryParamDecoder[Action] = 
    QueryParamDecoder[String]
      .map(e => Try(Action.valueOf(e)).toEither)
      .emap(either => either.left.map(e => ParseFailure("Action is invalid or not supported", e.getMessage)))