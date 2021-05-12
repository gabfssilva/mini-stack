package sns

import cats.effect._
import cats.syntax.all._

import cats.data.OptionT

import models._

class SnsService(topics: SnsTopics,
                 subscriptons: SnsSubscriptions,
                 notificationService: NotificationService):
  def deleteTopic(arn: String) = topics.deleteTopic(arn)

  def createTopic(name: String) = topics.createTopic(name)

  def listAll = topics.listAll

  def subscribe(topicArn: String, 
                endpoint: String,
                protocol: SubscriptionProtocol): IO[Option[Subscription]] =
    (for
      topic        <- OptionT(topics.byArn(topicArn))
      subscription <- OptionT.liftF(subscriptons.create(topic, endpoint, protocol))
    yield subscription).value

  def publish(topicArn: String, message: String) = 
    (for
      topic  <- OptionT(topics.byArn(topicArn))
      _      <- OptionT.liftF(notificationService.notify(topic, message))
    yield ()).value
