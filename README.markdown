##What is Scalarm?

It is a simple alarm/timeout that you can set in JVM based/friendly languages.

##Why would I ever need such a thing? There are a dozen ways to do it.

Most of the time when there is a need for a timeout there are several approaches that are used:

* Thread.sleep( milliseconds )

    => probably the most common and dirty/blocking approach that is seen and used around dev universe 
* Time the "now", and loop until the "current time - now" reaches the desired interval

    => a bit too splashed around the code, plus needs to be repeated every time a timeout is needed
* Submit a Callable task of what you want to execute for certain interval to a [ScheduledExecutorService](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ScheduledExecutorService.html)

    => which is not all that bad, but makes it a bit difficult to wrap the timeout around an existing code block, plus is hard to combine with other conditions (more about it below)

* Submit a Callable task of what you want to execute to [ExecutorService](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html) and block the future with a timeout e.g "future.get(10, TimeUnit.SECONDS)"

    => similar difficulties as with ScheduledExecutorService above

There are others e.g. Clojail's quite convenient [thunk-timeout](https://github.com/flatland/clojail/blob/master/src/clojail/core.clj#L34), AKKA's [Deadline](http://doc.akka.io/docs/akka/snapshot/common/duration.html#Deadline), etc..

Scalarm allows you do do all the above minus the difficulties, plus a little bonus feature that I find quite useful.

##Again, why would I use it?

Imagine that a timeout alone is not enough to "let go" and there are other _conditions_ that can/should allow to break out?

For example, let's say you'd like to pay a developer $800 a day or have her work for 8 hours: **whichever comes first**. And let's say every time a developer delivers a feature you pay her $100.

Scalarm just takes care of an alarm nothing else => it does not take a task, does block an execution, does not need nor produce anything (e.g. no futures) but the alarm itself.
What Scalarm also has is an indicator of whether an alarm went off or not. Hence to code the above money/hours/developer problem would be as simple as:

```scala
val alarm = timeOut( 8, TimeUnit.HOURS )

while ( ( alarm.didNotGoOff ) && ( developer.pocket < 800 ) ) {
  developer.deliverFeature()
}
```
where delivering feature adds $100 to the developer pocket. And of course there may be many more conditions added, since now a timeout is decoupled from the actual thing that needs to be done.

##How do I use Scalarm?

Example above describes it all really. But to clarify:

###Setting an alarm

```scala
val alarm = timeOut( <interval>, <time unit> )
```

for example:

```scala
val alarm = timeOut( 42, TimeUnit.MILLISECONDS )
```

Which creates _and starts_ a new alarm right away.

###Checking whether an alarm went off

Is as simple as:

```scala
if ( alarm.wentOff )          // will return true if an alarm did go off
```

or

```scala
while ( alarm.didNotGoOff )   // will spin until an alarm goes off
```

The reason for both "didNotGoOff" and "wentOff" is readability. For example, a while loop reads better as "while ( alarm.didNotGoOff )", however in case somebody just comes back later and checks whether alarm went off, it reads a little better as "if ( alarm.wentOff )".

##Examples

For more examples check out the [AlarmUsage](https://github.com/tolitius/scalarm/blob/master/src/test/scala/org/gitpod/alarm/AlarmUsage.scala). 

They all can be run with

```bash
$ sbt test
```
