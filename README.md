# Plumber

Plumber is targeted at providing dynamic customized streaming architecture without coding efforts.
The project is implemented based on spark streaming.

## Concepts

The plumber needs to assemble the whole pipework, to provide a channel for the steam to go through.
We borrow some idea of [Gobblin](https://github.com/linkedin/gobblin) in modeling pipework, as follows.

<pre>

 +-------+               +-------+                 +-------+
 |       +---------------+       +-----------------+       |
 | Inlet |     Valve*    | Joint |     Valve*      |Outlet0|
 |       +---------------+       +-----------------+       |
 +-------+     Trunk     +-------+     Branch0     +-------+
                           |   |                   +-------+
                           |   +-------------------+       |
                           |           Valve*      |Outlet1|
                           +-----------------------+       |
                                       Branch1     +-------+
                                       
</pre>

The whole pipework consists of two parts: the *Trunk* channel and a set of *Branch* channels.

* Trunk: the trunk channel pulls source stream from Inlet, processes on-the-fly transformations via a sequence of Valves, and
generates the *to-fork* stream at Joint.

* Branch: every branch channel take a copy of to-fork stream, further processes it with another sequences of valves, publishes
the final version of stream to outlet.

### Inlet

Inlet is the *source* of the initial stream, such as that from apache log, sockets, Kafka, Twitter streaming, etc.

### Outlet

Outlet is the final store to consume/publish the targeet stream, including console dump, HDFS filesystem, different databases.

### Joint

A logic unit to connect the trunk and branches. It does nothing but providing multiple copys of the trunk stream, one per branch.

### Valve

Valve processes a specified transformation on the upstream and output the downstream. 

