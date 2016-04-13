package org.plumber

import org.plumber.api.{Rectifier, Inlet, Valve}

/**
 * Created by baihe on 16/4/13.
 */
case class Trunk(inlet: Inlet[Any], rectifier: Rectifier[Any], valves: Iterable[Valve])
