import cats.effect._
import cats.effect.concurrent._

import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._
import scala.concurrent.ExecutionContext.global

import sns._

import models._

object Main extends IOApp:
  def run(args: List[String]): IO[ExitCode] =
    for
      snsTopics <- Ref[IO].of(Map.empty[String, Topic])

      exitCode  <- BlazeServerBuilder[IO](global)
                     .bindHttp(8080, "localhost")
                     .withHttpApp(SnsController.service(new SnsTopics(snsTopics, Region.UsEast1)).orNotFound)
                     .serve
                     .compile
                     .drain
                     .as(ExitCode.Success)
    yield exitCode
