    import {createPlayerViewElement} from "./playerView.js"
    var ws = new WebSocket("ws://localhost:4567/roomJoin");
    var playerListHTML = document.getElementById("playerList");
    var gameView = document.getElementById("game_container");
    var players = [];


    players.forEach(player => {
        createPlayerViewElement(player);
    })

    ws.onopen = function (event) {
        console.log("Websocket to Room is open");
        let joinRequest = {
                    messageType: "getRoomName",
                    content: roomName
                }

        ws.send(JSON.stringify(joinRequest));
    }

    ws.onclose = function (event) {
        console.log("Websocket to Rooms is closed");

    }

    ws.onmessage = function (event) {
        let data = JSON.parse(event.data);
        processMessage(data);

    }

    function processMessage(data) {
        let message = data.messageType;
        if (message === "playerConnection") {
            console.log("New player joined room");
            players = data.players;
            console.log(players)

            playerListHTML.innerHTML = "";
            
            players.forEach(player => {
                playerListHTML.innerHTML += "<li> " + player.name + "</li>";
                createPlayerViewElement(player)
            });
        }
    }

    window.onbeforeunload = function() {
        let closeRequest = {
            messageType: "playerClose",
            content: roomName + " " + playerName
        }

        ws.send(JSON.stringify(closeRequest));
    }