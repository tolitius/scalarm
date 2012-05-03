package org.gitpod.alarm

import java.util.concurrent.{TimeUnit, Executors}

/**
 * Given an [interval] and a [TimeUnit], sets an alarm which goes off after a this [interval]
 *
 * @author anatoly.polinsky
 */
object timeOut extends ( ( Int, TimeUnit ) => Alarm ) {

  def apply( interval: Int, timeUnit: TimeUnit ): Alarm = {

    var alarm = new Alarm

    val scheduler = Executors.newScheduledThreadPool( 1 )
    scheduler.schedule(
      new Runnable() {
        def run() {
          alarm.buzz()
          scheduler.shutdown()
        } }, interval , timeUnit )

    alarm
  }
}
