package endpoints

import org.http4s.dsl.io._
import org.http4s._

import models._

import scala.language.dynamics
    
enum QueryParameter[T: QueryParamDecoder](name: String) extends QueryParamDecoderMatcher[T](name):
  case Name      extends QueryParameter[String]("Name")
  case Action    extends QueryParameter[sns.Action]("Action")
  case Version   extends QueryParameter[String]("Version")
  case Message   extends QueryParameter[String]("Message")
  case Endpoint  extends QueryParameter[String]("Endpoint")
  case Protocol  extends QueryParameter[SubscriptionProtocol]("Protocol")
  case TopicArn  extends QueryParameter[String]("TopicArn")
  case TargetArn extends QueryParameter[String]("TargetArn")
