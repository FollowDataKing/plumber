package org.plumber

import org.plumber.api.{Extractor, Inlet, Valve}

/**
 * Created by baihe on 16/4/13.
 */
case class Trunk(inlet: Inlet[Any], rectifier: Extractor[Any], valves: Iterable[Valve])
