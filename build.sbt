ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.7"
val akkaHttpVersion = "10.1.7"
val akkaVersion = "2.6.20"
val akkaStream: ModuleID = "com.typesafe.akka" %% "akka-stream" % akkaVersion
val akkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % "10.2.10"
lazy val root = (project in file("."))
  .settings(
    name := "QuoteExtractionService",
    libraryDependencies+="io.cequence" %% "openai-scala-client" % "0.1.0",
    libraryDependencies += akkaStream,
    // akka http
    libraryDependencies += akkaHttp
  )
