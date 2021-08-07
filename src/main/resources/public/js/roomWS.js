    import {createPlayerViewElement, addCardToPlayerView} from "./playerView.js"
    
    var ws = new WebSocket("ws://localhost:4567/roomJoin");
    var playerListHTML = document.getElementById("playerList");
    var gameView = document.getElementById("game_container");

    var startButton = document.getElementById("start_btn");
    startButton.addEventListener("click", startGame)

    var players = [];

    function startGame() {

        startButton.hidden = true;
        startButton.disabled = true;

        let startGameRequest = {
            messageType: "startGame",
            content: window.roomName + " " + window.playerName
        }

        ws.send(JSON.stringify(startGameRequest))
    }


    players.forEach(player => {
        createPlayerViewElement(player);
    })

    ws.onopen = function (event) {
        console.log("Websocket to Room is open");
        let joinRequest = {
            messageType: "getRoomName",
            content: window.roomName + " " + window.playerName
        }
        ws.send(JSON.stringify(joinRequest))
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
        if (message === "playerList") {
            console.log("New player joined room");
            let players = data.players
            
            players.forEach(player => {
                createPlayerViewElement(player)
            })
            

        }
        else if (message === "DISCONNECT") {
            console.log("Player left")
            let playerLeaving = data.player
            let elem = document.getElementById("playerId_" + playerLeaving.name)
            elem.remove()
        }
        else if (message === "INITIAL_DEAL") {
            let card = data.card
            let player = data.player

            addCardToPlayerView(player, card)



        }

        else if (message === "CLEAR_HAND") {
            players.forEach(player => {
                elem = document.getElementById(player.name + "_card_view")
                if (elem !== null)
                    elem.innerHTML = "";
            })
        }

        else if (message === "START_ROUND") {
            if (data.activePlayer.name === window.playerName) {
                document.getElementById("hit_btn").disabled = false;
                document.getElementById("stand_btn").disabled = false;
            }
            else {
                document.getElementById("hit_btn").disabled = true;
                document.getElementById("stand_btn").disabled = true;
            }

            console.log(data.activePlayer.name)
        }
    }

    window.onbeforeunload = function() {
        let closeRequest = {
            messageType: "playerClose",
            content: roomName + " " + playerName
        }


        ws.send(JSON.stringify(closeRequest));
    }