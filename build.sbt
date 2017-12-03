enablePlugins(JavaAppPackaging)

name := "sangria-akka-http-circe-example"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.4"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

val akkaVersion      = "2.5.7"
val akkaHttpVersion  = "10.0.10"
val circeVersion     = "0.8.0"
val akkaCirceVersion = "1.18.0"
val mysqlConnectorVersion = "5.1.44"
val hikariVersion = "2.7.2"
val scalaTestVersion = "3.0.4"

val sangriaVersion = "1.3.0"
val sangriaCirceVersion =  "1.1.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.sangria-graphql" %% "sangria" % sangriaVersion,
  "org.sangria-graphql" %% "sangria-circe" % sangriaCirceVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "de.heikoseeberger" %% "akka-http-circe" % akkaCirceVersion,
  "mysql" % "mysql-connector-java" % mysqlConnectorVersion,
  "com.zaxxer" % "HikariCP" % hikariVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion  % "test"
)

mainClass in Compile := Some("Main")
