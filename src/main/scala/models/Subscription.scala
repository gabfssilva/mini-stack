package models

import org.http4s._

import java.util.UUID

enum SubscriptionProtocol:
  case Http
  case Https
  case Sqs

object SubscriptionProtocol:
  def byNameInsensitive(name: String): Option[SubscriptionProtocol] =
    SubscriptionProtocol.values
      .filter(v => v.toString.toLowerCase == name.toLowerCase)
      .headOption

  given QueryParamDecoder[SubscriptionProtocol] = 
    QueryParamDecoder[String]
      .map(SubscriptionProtocol.byNameInsensitive)
      .emap:
        case None        => Left(ParseFailure("Invalid protocol", "The provided value is not a valid protocol"))
        case Some(value) => Right(value)

final case class Subscription(arn: String,
                              topic: Topic,
                              endpoint: String,
                              protocol: SubscriptionProtocol)

object Subscription:
  def apply(topic: Topic,
            endpoint: String,
            protocol: SubscriptionProtocol) =
    new Subscription(
      arn = s"${topic.arn}:${UUID.randomUUID}",
      topic = topic,
      endpoint = endpoint,
      protocol = protocol
    )        