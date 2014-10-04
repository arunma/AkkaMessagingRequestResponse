import com.typesafe.sbt.SbtAspectj._

name := """AkkaMessagingReqRes"""

version := "2.3.5"

scalaVersion := "2.11.2"

val kamonVersion = "0.3.4"

resolvers ++=
        Seq("repo" at "http://repo.typesafe.com/typesafe/releases/")

libraryDependencies ++= {
	val akkaVersion = "2.3.4"
	Seq(
  		"com.typesafe.akka" %% "akka-actor" % "2.3.4",
  		"io.kamon" %% "kamon-core" % kamonVersion,
  		"io.kamon" %% "kamon-statsd" % kamonVersion,
  		"io.kamon" %% "kamon-log-reporter" % kamonVersion,
  		"io.kamon" %% "kamon-system-metrics" % kamonVersion,
  		"org.aspectj" % "aspectjweaver" % "1.8.1",
  		"com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  		"ch.qos.logback" % "logback-classic" % "1.1.2",
  		"com.typesafe.akka" %% "akka-testkit" % akkaVersion, 
  		"org.scalatest" %% "scalatest" % "2.2.0"
		)
}

aspectjSettings

javaOptions <++= AspectjKeys.weaverOptions in Aspectj

fork in run := true

