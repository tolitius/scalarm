import sbt._
import sbt.Keys._

object ProjectBuild extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "scalarm",
      organization := "org.gitpod",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.1",
      resolvers += "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
      libraryDependencies += "org.specs2" %% "specs2" % "1.9" % "test"
    )
  )
}
