


    var ws = new WebSocket("ws://localhost:4567/roomJoin");
    var playerListHTML = document.getElementById("playerList");
    var players = [];

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
            players.forEach(element => {
                playerListHTML.innerHTML += "<li> " + element.name + "</li>";
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