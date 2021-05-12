package sns

import cats.effect._
import cats.effect.concurrent.Ref
import cats.syntax.all._

import models._

class SnsSubscriptions(subscriptons: Ref[IO, Map[String, Subscription]]):
  def allByTopicArn(topicArn: String): IO[List[Subscription]] =
    subscriptons
      .get
      .map(m => m.values.filter(s => s.topic.arn == topicArn).toList)

  def create(topic: Topic,
             endpoint: String,
             protocol: SubscriptionProtocol): IO[Subscription] =
    val subscription = Subscription(topic, endpoint, protocol)

    subscriptons
      .modify(map => ((map + (subscription.arn -> subscription)), map))
      .map(_ => subscription)

