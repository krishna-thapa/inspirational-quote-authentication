name := """inspiration-quote-authentication"""
organization := "com.krishna"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick"            % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
)

libraryDependencies += "com.h2database" % "h2" % "1.4.199"

resolvers += "Atlassian's Maven Public Repository" at "https://packages.atlassian.com/maven-public/"

libraryDependencies ++= Seq(
  "com.mohiva"     %% "play-silhouette"                 % "7.0.0",
  "com.mohiva"     %% "play-silhouette-password-bcrypt" % "7.0.0",
  "com.mohiva"     %% "play-silhouette-crypto-jca"      % "7.0.0",
  "com.mohiva"     %% "play-silhouette-persistence"     % "7.0.0",
  "com.mohiva"     %% "play-silhouette-testkit"         % "7.0.0" % "test",
  "net.codingwell" %% "scala-guice"                     % "4.2.6",
  "org.postgresql" % "postgresql"                       % "9.4-1201-jdbc41"
)

libraryDependencies += specs2 % Test

/*scalacOptions ++= Seq(
  "-deprecation",            // Emit warning and location for usages of deprecated APIs.
  "-feature",                // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked",              // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings",        // Fail the compilation if there are any warnings.
  "-Xlint",                  // Enable recommended additional warnings.
  "-Ywarn-adapted-args",     // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code",        // Warn when dead code is identified.
  "-Ywarn-inaccessible",     // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen"     // Warn when numerics are widened.
)*/
