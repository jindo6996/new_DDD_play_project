name := "appstart"
 
version := "1.0" 
      
lazy val `appstart` = (project in file(".")).enablePlugins(PlayScala)

lazy val commonSettings = Seq(
  scalaVersion := "2.12.2",
  libraryDependencies ++= Seq(
    guice,
    jdbc,
    evolutions,
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
    "mysql" % "mysql-connector-java" % "5.1.36",
    "org.scalikejdbc" %% "scalikejdbc"                  % "3.0.1",
    "org.scalikejdbc" %% "scalikejdbc-config"           % "2.5.1",
    "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0",
    "ch.qos.logback"  %  "logback-classic"   % "1.2.3",
    specs2 % Test,
    "org.mindrot" % "jbcrypt" % "0.3m"
  )
)

lazy val root = (project in file("."))
  .aggregate(application, domain, infra)
  .settings(
    run := {
      (run in application in Compile).evaluated
    }
  )

lazy val application = (project in file("application"))
  .dependsOn(domain, infra)
  .enablePlugins(PlayScala)
  .settings(commonSettings)
  .settings(
    routesGenerator := InjectedRoutesGenerator,
    parallelExecution in Test := false
  )

lazy val domain = (project in file("domain"))
  .dependsOn(infra)
  .settings(commonSettings)
  .settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    parallelExecution in Test := false
  )

lazy val infra = (project in file("infra"))
  .settings(commonSettings)
  .settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources",
    parallelExecution in Test := false
  )

import scalariform.formatter.preferences._

scalariformPreferences := scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentConstructorArguments, true)
  .setPreference(DanglingCloseParenthesis, Preserve)