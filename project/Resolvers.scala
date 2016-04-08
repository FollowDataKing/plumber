/**
 * Created by baihe on 16/4/8.
 */

import sbt._

object Resolvers {
  val customizedResolvers = Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("public"),
    "Nexusosc" at "http://maven.oschina.net/content/groups/public/",
    "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
    "Spray Repository" at "http://repo.spray.cc/",
    "Cloudera Repository" at "https://repository.cloudera.com/artifactory/repo/",
    "Akka Repository" at "http://repo.akka.io/releases/",
    "Twitter4J Repository" at "http://twitter4j.org/maven2/",
    "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
    "Twitter Maven Repo" at "http://maven.twttr.com/",
    "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
    "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
    "Conjars" at "http://conjars.org/repo/"
  )
}

