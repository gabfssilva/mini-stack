package sns

import cats.effect._

import endpoints.HttpService

import io.circe.generic.auto._
import io.circe.syntax._

import org.http4s._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

import endpoints.XmlCodec.xmlEncoder

import models._
import endpoints._

import scala.concurrent.ExecutionContext.Implicits.global

object SnsController:
  def service(sns: SnsService) = HttpRoutes.of[IO]:
    case POST -> Root / "sns" :? QueryParameter.Action(Action.CreateTopic) 
                              :? QueryParameter.Name(name) =>
      for
        topic     <- sns.createTopic(name)
        response  <- Ok(SnsResponses.createTopicResponse(topic))
      yield response
        
    case POST -> Root / "sns" :? QueryParameter.Action(Action.ListTopics) =>
      for
        snsTopics <- sns.listAll
        response  <- Ok(SnsResponses.listTopicsResponse(snsTopics))
      yield response

    case POST -> Root / "sns" :? QueryParameter.Action(Action.DeleteTopic)
                              :? QueryParameter.TopicArn(topicArn) =>
      for
        _        <- sns.deleteTopic(topicArn)
        response <- Ok(SnsResponses.deleteTopicResponse)
      yield response

    case POST -> Root / "sns" :? QueryParameter.Action(Action.Publish)
                              :? QueryParameter.TopicArn(topicArn)
                              :? QueryParameter.Message(message) =>
      for
        maybeSuccess  <- sns.publish(topicArn, message)
        response      <- maybeSuccess match
                           case None     => NotFound()
                           case Some(()) => Ok(SnsResponses.publishResponse)
      yield response

    case POST -> Root / "sns" :? QueryParameter.Action(Action.Subscribe)
                              :? QueryParameter.TopicArn(topicArn)
                              :? QueryParameter.Endpoint(endpoint)
                              :? QueryParameter.Protocol(protocol) =>
      for
        maybeSubscription <- sns.subscribe(topicArn, endpoint, protocol)
        response          <- maybeSubscription match
                               case None               => NotFound()
                               case Some(subscription) => Ok(SnsResponses.subscriptionResponse(subscription))
      yield response

end SnsController