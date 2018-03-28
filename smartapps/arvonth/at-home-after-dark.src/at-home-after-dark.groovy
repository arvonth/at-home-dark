/**
 *  At Home After Dark
 *
 *  Copyright 2018 Arvont Hill
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "At Home After Dark",
    namespace: "arvonth",
    author: "Arvont Hill",
    description: "In general this SmartApp turns on a device when a door opens, but only if it is at or past sunset.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Turn on when Door is opened:") {
        input "thesensor", "capability.contactSensor", required: true, title: "Where?"
    }
    section("Turn on this light") {
        input "theswitch", "capability.switch", required: true
    }
    section("Zip code") {
        input "zipCode", "text", required: true
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(thesensor, "contact.closed", doorClosedHandler)
    subscribe(thesensor, "contact.open", doorOpenHandler)
}



def doorOpenHandler(evt) {
    log.debug "doorOpenHandler called: $evt"

    def s = getSunriseAndSunset(zipCode: zipCode)
    def setTime = s.sunset
    def riseTime = s.sunrise
    def now = new Date()
    
    //Get Current Time and Compare to Sunset Time
    log.debug "Time now $now"
    log.debug "sunset at the location of your hub $setTime"
    log.debug "sunrise at the location of your hub $riseTime"
    
    if ( now.after(setTime) ) {
    	theswitch.on()
        log.debug "Switch: ON"
    }
}

def doorClosedHandler(evt) {
	log.debug "doorClosedHandler called: $evt"
}
