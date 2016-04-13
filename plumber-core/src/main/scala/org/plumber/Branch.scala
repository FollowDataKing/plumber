package org.plumber

import org.plumber.api.{DeRectifier, Outlet, Valve}

/**
 * Created by baihe on 16/4/13.
 */
case class Branch(valves: Iterable[Valve], deRectifier: DeRectifier[Any], outlet: Outlet[Any])
