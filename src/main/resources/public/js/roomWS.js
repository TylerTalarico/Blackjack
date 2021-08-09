    import {createPlayerViewElement, addCardToPlayerView} from "./playerView.js"
    
    var ws = new WebSocket("ws://localhost:4567/roomJoin");
    var playerListHTML = document.getElementById("playerList");
    var gameView = document.getElementById("game_container");

    var startButton = document.getElementById("start_btn");
    var hitButton = document.getElementById("hit_btn");
    var standButton = document.getElementById("stand_btn");

    startButton.addEventListener("click", startGame)
    hitButton.addEventListener("click", hit)
    standButton.addEventListener("click", stand)

    var players = [];

    function startGame() {

        startButton.hidden = true;
        startButton.disabled = true;

        let startGameRequest = {
            messageType: "START_GAME",
            content: window.roomName + " " + window.playerName
        }

        ws.send(JSON.stringify(startGameRequest))
    }

    function hit() {

        disableActionButtons();

        let hitRequest = {
            messageType: "SUBMIT_MOVE",
            content: window.roomName + " " + window.playerName + " HIT"
        }
        console.log(hitRequest.content)
        ws.send(JSON.stringify(hitRequest))
    }

    function stand() {

        disableActionButtons();

        let standRequest = {
            messageType: "SUBMIT_MOVE",
            content: window.roomName + " " + window.playerName + " STAND"
        }
        ws.send(JSON.stringify(standRequest))
    }


    players.forEach(player => {
        createPlayerViewElement(player);
    })

    ws.onopen = function (event) {
        console.log("Websocket to Room is open");
        let joinRequest = {
            messageType: "GET_ROOM_NAME",
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
        if (message === "PLAYER_LIST") {
            console.log("New player joined room");
            players = data.players
            
            players.forEach(player => {
                createPlayerViewElement(player)
            })
            

        }
        else if (message === "DISCONNECT") {
            console.log("Player left")
            let playerLeaving = data.player
            let elem = document.getElementById("playerId_" + playerLeaving.name)
            elem.remove()
            players.remove(playerLeaving);
        }
        else if (message === "INITIAL_DEAL") {
            let card = data.card
            let player = data.player

            addCardToPlayerView(player, card)



        }

        else if (message === "CLEAR_HAND") {
            players.forEach(player => {
                let elem = document.getElementById(player.name + "_card_view")
                if (elem !== null)
                    elem.innerHTML = "";
            })
            console.log(players)
        }

        else if (message === "START_ROUND") {
            if (data.activePlayer.name === window.playerName) {
                enableActionButtons();
            }
            else {
                 disableActionButtons();
            }

            console.log(data.activePlayer.name)
        }

        else if (message === "HIT") {
            addCardToPlayerView(data.activePlayer, data.card)

            if (window.playerName === data.newActivePlayer.name) {
                enableActionButtons();
            }
        }

        else if (message === "STAND") {
            if (window.playerName === data.newActivePlayer.name) {
                enableActionButtons();
            }
        }

        else if (message === "ROUND_OVER") {
            console.log(data.winner.name + " Won the Round!")
        }

        else if (message === "GAME_OVER") {
                    console.log(data.winner.name + " Won the Game!")
                }
    }

    function enableActionButtons() {
        hitButton.disabled = false;
        standButton.disabled = false;
    }

    function disableActionButtons() {
        hitButton.disabled = true;
        standButton.disabled = true;
    }

    window.onbeforeunload = function() {
        let closeRequest = {
            messageType: "PLAYER_CLOSE",
            content: roomName + " " + playerName
        }


        ws.send(JSON.stringify(closeRequest));
    }