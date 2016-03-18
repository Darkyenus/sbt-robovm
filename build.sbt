lazy val roboVMVersion = settingKey[String]("RoboVM Version against which this plugin is built")

lazy val sbtRoboVM = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    name := "sbt-robovm",
    roboVMVersion := "1.14.0",
    licenses += ("BSD 3-Clause", url("http://opensource.org/licenses/BSD-3-Clause")),
    organization := "org.roboscala",
    version := roboVMVersion.value,
    publishM2 := {
      publishM2.value

      val d = file(sys.env("HOME")) / s".m2/repository/org/roboscala/sbt-robovm_${scalaBinaryVersion.value}_${sbtBinaryVersion.value}"
      d.renameTo(file(sys.env("HOME")) / ".m2/repository/org/roboscala/sbt-robovm")
    },
    sbtPlugin := true,
    publishMavenStyle := true,
    bintrayOrganization := None,
    bintrayRepository := "sbt-plugins",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-Xfatal-warnings"),
    javacOptions ++= Seq("-source", "6", "-target", "6"),
    resolvers ++= {if (roboVMVersion.value.contains("-SNAPSHOT")) Seq(Resolver.sonatypeRepo("snapshots")) else Seq()},
    libraryDependencies += "org.robovm" % "robovm-dist-compiler" % roboVMVersion.value,
    libraryDependencies += "org.robovm" % "robovm-maven-resolver" % roboVMVersion.value,
    libraryDependencies += "org.robovm" % "robovm-templater" % roboVMVersion.value,
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, roboVMVersion),
    buildInfoPackage := "sbtrobovm"
  )
