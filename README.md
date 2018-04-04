# at-home-dark

The intent of this SmartApp is to turn on a switch when I arrive home after sunset.

The initial implementation monitors my garage door sensor for an open event and if it is past sunset then a swith is turned on.

It was later revised to use the presence sensor functionality that is available from the SmartThgings app.  I guess it can be used with other presence sensors as well.  I had to change it use presence because the original logic would turn on the switch when I was leaving too.
