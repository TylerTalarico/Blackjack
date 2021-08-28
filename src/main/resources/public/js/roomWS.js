    import {createPlayerViewElement, addCardToPlayerView} from "./playerView.js"
    import { testPlayers } from "./testPlayers.js";
    
    var ws = new WebSocket("ws://localhost:4567/roomJoin");
    var playerListHTML = document.getElementById("playerList");
    var gameView = document.getElementById("game_container");

    var startButton = document.getElementById("start_btn");
    var hitButton = document.getElementById("hit_btn");
    var standButton = document.getElementById("stand_btn");

    startButton.addEventListener("click", startGame)
    hitButton.addEventListener("click", hit)
    standButton.addEventListener("click", stand)

    //var players = testPlayers;
    var players = []

    disableActionButtons();

    // Uncomment to create test player elements
    // players.forEach(player => {
    //     createPlayerViewElement(player);
    // })


    function startGame() {

        startButton.hidden = true;
        startButton.disabled = true;

        let startGameRequest = {
            messageType: "START_GAME",
            content: window.roomName + "<>" + window.playerName
        }

        ws.send(JSON.stringify(startGameRequest))
    }

    function hit() {

        disableActionButtons();

        let hitRequest = {
            messageType: "SUBMIT_MOVE",
            content: window.roomName + "<>" + window.playerName + "<>HIT"
        }
        console.log(hitRequest.content)
        ws.send(JSON.stringify(hitRequest))
    }

    function stand() {

        disableActionButtons();

        let standRequest = {
            messageType: "SUBMIT_MOVE",
            content: window.roomName + "<>" + window.playerName + "<>STAND"
        }
        ws.send(JSON.stringify(standRequest))
    }


    

    ws.onopen = function (event) {
        console.log("Websocket to Room is open");
        let joinRequest = {
            messageType: "GET_ROOM_NAME",
            content: window.roomName + "<>" + window.playerName
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
        console.log(data)
        let message = data.messageType;
        if (message === "PLAYER_LIST") {
            console.log("New player joined room");
            players = data.players
            
            players.forEach(player => {
                createPlayerViewElement(player)
            })
            

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

        else if (message === "ROUND_OVER") {
            displayMessage(data.winner.name + " Won the Round!")
            console.log(data.winner.name + " Won the Round!")
        }

        else if (message === "GAME_OVER") {
            displayMessage(data.winner.name + " Won the Game!")
            console.log(data.winner.name + " Won the Game!")
        }

        else {

            checkIfCurrentPlayer(data.newActivePlayer)

            if (message === "DISCONNECT") {
                console.log("Player left")
                let playerLeaving = data.playerLeaving
                let elem = document.getElementById("playerId_" + playerLeaving.name)
                elem.remove()

                let i = players.indexOf(playerLeaving)

                if (i !== -1)
                    players.splice(i);

                displayMessage(playerLeaving.name + " has left")
            }

            else if (message === "START_ROUND") {

                console.log(data.newActivePlayer.name)
            }


            else if (message === "HIT") {
                addCardToPlayerView(data.activePlayer, data.card)

                if (data.bust) {
                    displayMessage(data.activePlayer.name + " busted!")
                }
            }

            else if (message === "STAND") {

                displayMessage(data.activePlayer.name + " has stood at " + data.activePlayer.hand.handTotal)
            }

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

    function checkIfCurrentPlayer(currentPlayer) {
        if (currentPlayer !== null) {
            if (currentPlayer.name === window.playerName)
            enableActionButtons()
        }

        else {
            disableActionButtons()
        }
    }

    function displayMessage(message) {
        let messageElement = document.createElement("h1")
        messageElement.setAttribute("class", "game-notification")
        messageElement.innerText = message

        document.getElementById("notifications").appendChild(messageElement)

        setTimeout( function() {
            messageElement.remove()
        }, 3000)

        

    }

    window.onbeforeunload = function() {
        
    
        let closeRequest = {
            messageType: "PLAYER_CLOSE",
            content: roomName + "<>" + playerName
        }


        ws.send(JSON.stringify(closeRequest));

        xhtml = new XMLHttpRequest()
        xhtml.open("POST", "/signOut", false)
        xhtml.send();
    }