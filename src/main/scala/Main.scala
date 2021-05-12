import cats.effect._
import cats.effect.concurrent._

import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._
import org.http4s.client.blaze._
import org.http4s.client._
import scala.concurrent.ExecutionContext.global

import sns._

import models._

object Main extends IOApp:
  def snsApp(region: Region, client: Client[IO]): IO[HttpRoutes[IO]] =
    for
      snsTopicsRef          <- Ref[IO].of(Map.empty[String, Topic])
      snsSubscriptionsRef   <- Ref[IO].of(Map.empty[String, Subscription])
      snsTopics             <- IO(new SnsTopics(snsTopicsRef, region))
      snsSubscriptions      <- IO(new SnsSubscriptions(snsSubscriptionsRef))
      notificationService   <- IO(new NotificationService(snsSubscriptions, client))
      snsService            <- IO(new SnsService(snsTopics, snsSubscriptions, notificationService))
    yield SnsController.service(snsService)

  def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO](global).resource.use:
      client =>
        for
          region   <- IO(Region.UsEast1)
          sns      <- snsApp(region, client)

          exitCode <- BlazeServerBuilder[IO](global)
                        .bindHttp(8080, "localhost")
                        .withHttpApp(sns.orNotFound)
                        .serve
                        .compile
                        .drain
                        .as(ExitCode.Success)
        yield exitCode
