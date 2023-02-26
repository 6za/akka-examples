ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "dockerhostapi",
    idePackagePrefix := Some("tech.code")
  )
// this will add the ability to "stage" which is required for Heroku
enablePlugins(JavaAppPackaging)

val akkaVersion = "2.6.5"
val akkaHttpVersion = "10.1.12"
fork in run := true

libraryDependencies ++= Seq(
  // akka streams
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
)