package sns

import cats.syntax.parallel._
import cats.effect._

import models._
import sns._

import scala.concurrent.ExecutionContext

import org.http4s.client.blaze._
import org.http4s.client._
import org.http4s._

import cats.syntax.all._

import fs2._

class NotificationService(subscriptions: SnsSubscriptions,
                          httpClient: Client[IO]):
  given ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  def notify(topic: Topic, message: String): IO[Fiber[IO, Unit]] =
    (for
      list <- subscriptions.allByTopicArn(topic.arn)
      _    <- list
                .map(s => notifyEach(message, s))
                .parSequence
    yield ()).start

  private def notifyEach(message: String, subscription: Subscription): IO[Unit] =
    subscription.protocol match
      case SubscriptionProtocol.Http | SubscriptionProtocol.Https =>
        val request = 
          Request[IO](
            method = Method.POST,
            body = Stream(message.getBytes:_*)
          )

        httpClient
          .run(request)
          .use:
            case Status.Successful(r) =>
              println("ué")
              IO.unit
            case _ => 
              println("ops")
              IO.raiseError(new RuntimeException("Invalid status"))
          .recoverWith:
            case e => 
              println(e.getMessage)
              IO.raiseError(e)

      case SubscriptionProtocol.Sqs  =>
        IO.unit