package endpoints

import org.http4s.HttpRoutes
import cats.effect.IO

trait HttpService {
  def service: HttpRoutes[IO]
}
