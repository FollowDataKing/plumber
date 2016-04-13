/**
 * Created by baihe on 16/4/8.
 */

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", xs@_*) => MergeStrategy.first
  case PathList("com", "google", xs@_*) => MergeStrategy.first
  case PathList("com", "esotericsoftware", "minlog", xs@_*) => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}
