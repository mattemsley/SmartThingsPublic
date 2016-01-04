/**
 *  DSC Alarm Button
 *
 *  Author: Rob Fisher <robfish@att.net>
 *  Date: 2015-02-26
 */
 // for the UI

preferences {
    input("ip", "text", title: "IP", description: "The IP of your alarmserver")
    input("port", "text", title: "Port", description: "The port")
} 
 
metadata {
	// Automatically generated. Make future change here.
	definition (name: "DSC Alarm Button", author: "Rob Fisher <robfish@att.net>") {
		capability "Switch"
	}

	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles {
		standardTile("button", "device.switch", width: 1, height: 1, canChangeIcon: true) {
			state "off", label: 'Ready', action: "switch.on", icon: "st.Home.home4", backgroundColor: "#79b821"
			state "on", label: 'Armed', action: "switch.off", icon: "st.Home.home4", backgroundColor: "#800000"
    }
		main "button"
		details(["button"])
	}
}

def parse(String description) {
}

def on() {
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/api/alarm/arm",
        headers: [
            HOST: "$ip:$port"
            //HOST: getHostAddress()
        ]
    )
    log.debug "response" : "Request to arm received"
    //log.debug "arm"
    sendEvent (name: "switch", value: "on")
    return result
}

def off() {
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/api/alarm/disarm",
        headers: [
            HOST: "$ip:$port"
            //HOST: getHostAddress()
        ]
    )
    log.debug "response" : "Request to disarm received"
    //log.debug "disarm"
    sendEvent (name: "switch", value: "off")
    return result
}
