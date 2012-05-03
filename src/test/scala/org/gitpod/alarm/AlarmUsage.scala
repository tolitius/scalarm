import java.util.concurrent.TimeUnit
import org.specs2.mutable._

import org.gitpod.alarm.timeOut

class AlarmUsage extends Specification {

  "Alarm" should {
 
    "go off after a given interval" in { 

      val dollars = makeMoneyFor( 2, TimeUnit.SECONDS )
      println( "not bad => your hardware hourly rate is $" + "%,d".format( dollars / 2 * 3600 )  )

      dollars must be_> ( 0L )
    }
  }

  "Million Dollars" should {
  
    "should be made as fast as possible" in { 

      val millionDollars = makeMillionDollarsFor( 3, TimeUnit.SECONDS )
      println( "niice => you just made a $" + "%,d".format( millionDollars ) )

      millionDollars must_== ( 1000000 )
    }

    "should not be made for a time given" in { 

      val change = makeMillionDollarsFor( 1, TimeUnit.MICROSECONDS )
      println( "great => you made some pocket change $" + "%,d".format( change ) )

      change must be_< ( 1000000L )
    }
  }

  def makeMoneyFor( interval: Int, timeUnit: TimeUnit ): Long = {

    val alarm = timeOut( interval, timeUnit )
    var dollars = 0L

    while ( ! alarm.wentOff ) {
      dollars += 1
    }

    dollars
  }

  def makeMillionDollarsFor( interval: Int, timeUnit: TimeUnit ): Long = {

    val alarm = timeOut( interval, timeUnit )
    var dollars = 0L

    while ( ( alarm.didNotGoOff ) && ( dollars < 1000000 ) ) {
      dollars += 1
    }

    dollars
  }
}
