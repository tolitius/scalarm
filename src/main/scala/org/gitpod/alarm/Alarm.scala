package org.gitpod.alarm

/**
 * When [done], [buzz]ed in by [[org.gitpod.alarm.timeOut]] 
 *
 * Allows to peek into its state via [didNotGoOff] and [wentOff]
 *
 * @author anatoly.polinsky
 */
class Alarm( @volatile private var done: Boolean = false ) {

  private [alarm] def buzz() { done = true }

  def didNotGoOff = { ! done }
  def wentOff = { done }
}
