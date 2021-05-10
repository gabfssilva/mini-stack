scalaVersion := "3.0.0-RC2-bin-20210328-cca5f8f-NIGHTLY"

name := "mini-stack"
organization := "io.github.gabfssilva"
version := "0.0.1-SNAPSHOT"

scalacOptions ++= Seq(
  "-encoding", "utf8", 
  "-Xfatal-warnings", 
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps",
  "-language:experimental.fewerBraces"
)

val http4sVersion = "0.22.0-M7"
val circeVersion = "0.14.0-M5"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.http4s" %% "http4s-scala-xml" % http4sVersion,
  
  "io.circe"   %% "circe-generic" % circeVersion,

  "ch.qos.logback" % "logback-classic" % "1.2.3"
)