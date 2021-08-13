import {createRoomElement, adjustRoomSlotElements} from "../js/roomListView.js"
import {testRooms} from "../js/testRoom.js"

    var ws = new WebSocket("ws://localhost:4567/roomList");

    // Uncomment when working on page layout
    // testRooms.forEach(room => {
    //     createRoomElement(room.roomName, room.playerCap, room.pointCap)
    // }) 
    



    ws.onopen = function (event) {
        console.log("Websocket to Rooms is open");

    }

    ws.onclose = function (event) {
        console.log("Websocket to Rooms is closed");
    }

    ws.onmessage = function (event) {
        let data = JSON.parse(event.data)
        processMessage(data)
    }


    function processMessage(data) {
        
        let message = data.messageType

        if (message === "CREATE_ROOM") {
            console.log("Room Created")
            let rd = data.roomData
            console.log(rd.pointCap)
            createRoomElement(rd.roomName, rd.playerCap, rd.currentNumPlayers, rd.pointCap)
        }

        else if (message === "ROOM_LIST") {
            console.log(data)
            let rooms = data.roomDataList;
            rooms.forEach(room => {
                console.log("checkpoint 1")
                console.log(room.playerCap)
                createRoomElement(room.roomName, room.playerCap, room.currentNumPlayers, room.pointCap)
            })
        }

        else if (message === "REMOVE_ROOM") {
            let elem = document.getElementById("roomID_" + data.roomName)
            elem.remove()
        }

        else if (message === "ROOM_NUM_PLAYERS_UPDATE") {
            adjustRoomSlotElements(data.roomName, data.numPlayers)
        }
    
    }

    