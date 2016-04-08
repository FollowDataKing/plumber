/**
 * Created by baihe on 16/4/8.
 */

assemblyMergeStrategy in assembly := {
  // Resolve the duplicated UnusedStubClass
  case PathList("org", "apache", "spark", "unused", xs@_*) => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}
