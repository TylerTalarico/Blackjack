export function createRoomElement(roomName, playerCap, currentNumPlayers, pointCap) {
    let roomList = document.getElementById("room-list")

    console.log("checkpoint 2")

    let roomElement = document.createElement("li")
    roomElement.setAttribute("id", "roomId_" + roomName)

    let roomSlots = document.createElement("div")
    roomSlots.setAttribute("id", "slotId_" + roomName)
    console.log("Player Cap: " + playerCap)


    for (let i = 0; i < playerCap; i++) {
        let slot = document.createElement("div")
        if (i < currentNumPlayers) {
            slot.setAttribute("class", "filled slot")
        }
        else {
            slot.setAttribute("class", "unfilled slot")
        }


        roomSlots.appendChild(slot)
    }

    console.log("checkpoint 3")

    let pointCapElement = document.createElement("div")
    pointCapElement.setAttribute("class", "point-goal")
    pointCapElement.innerText = "Point Goal: " + pointCap

    let roomNameElement = document.createElement("a")
    roomNameElement.setAttribute("href", "/room?roomName=" + roomName)
    roomNameElement.setAttribute("class", "room-name")
    roomNameElement.innerText = roomName;

    roomElement.appendChild(roomSlots)
    roomElement.appendChild(pointCapElement)
    roomElement.appendChild(roomNameElement)

    
    
    roomList.appendChild(roomElement)

}

export function adjustRoomSlotElements(roomName, playerCount) {
    let slots = document.getElementById("slotId_" + roomName).childNodes

    for (let i = 0; i < slots.length; i++) {
        if (i < playerCount)
            slots[i].setAttribute("class", "filled slot")
        else
            slots[i].setAttribute("class", "unfilled slot")
    }
}