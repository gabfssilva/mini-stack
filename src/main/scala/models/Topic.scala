package models

final case class Topic(name: String,
                       arn: String)

object Topic:
  def apply(name: String, region: Region) =
    new Topic(name, s"arn:aws:sns:${region.value}:123456789012:$name")
