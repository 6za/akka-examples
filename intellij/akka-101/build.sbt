ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"
name := "akka-studies"

lazy val root = (project in file("."))
  .settings(
    name := "akka-101"
  )
// this will add the ability to "stage" which is required for Heroku
enablePlugins(JavaAppPackaging)

// this specifies which class is the main class in the package
mainClass in Compile := Some("AkkaHttpServer1")

val akkaVersion = "2.6.5"
val akkaHttpVersion = "10.1.12"

libraryDependencies ++= Seq(
  // akka streams
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion

)