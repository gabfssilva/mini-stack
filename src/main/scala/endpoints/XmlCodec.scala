package endpoints

import org.http4s._

import cats.effect.Concurrent
import java.io.{ByteArrayInputStream, StringWriter}
import javax.xml.parsers.SAXParserFactory

import org.http4s.headers.`Content-Type`

import scala.util.control.NonFatal
import scala.xml._

object XmlCodec:
  private val printer = PrettyPrinter(160, 2)

  given xmlEncoder[F[_], E <: Node]: EntityEncoder[F, E] =
    EntityEncoder
      .stringEncoder[F]
      .contramap[E](printer.format(_))
      .withContentType(`Content-Type`(MediaType.application.xml).withCharset(DefaultCharset))

