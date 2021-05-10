package sns

import cats.effect._
import cats.effect.concurrent.Ref
import cats.syntax.all._

import models._

class SnsTopics(topics: Ref[IO, Map[String, Topic]], region: Region):
  def deleteTopic(topicArn: String): IO[Unit] =
    topics
      .modify(map => ((map - topicArn), map))
      .flatMap(_ => IO.unit)

  def createTopic(name: String): IO[Topic] =
    val topic = Topic(name, region)

    topics
      .modify(map => ((map + (topic.arn -> topic)), map))
      .map { _ => topic }

  def listAll: IO[Set[Topic]] = topics.get.map { m => m.values.toSet }

