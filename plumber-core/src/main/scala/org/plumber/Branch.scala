package org.plumber

import org.plumber.api.{Publisher, Outlet, Valve}

/**
 * Created by baihe on 16/4/13.
 */
case class Branch(valves: Iterable[Valve], publisher: Publisher[Any], outlet: Outlet[Any])
