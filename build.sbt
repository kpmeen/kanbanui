import ScalaJSKeys._

scalaJSSettings

name := """kanbanui"""

version := "1.0"

scalaVersion := "2.11.2"

skip in packageJSDependencies := false
persistLauncher in Compile := true
persistLauncher in Test := false

libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6"
libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6"
libraryDependencies += "com.scalatags" %%% "scalatags" % "0.4.2"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.3"

