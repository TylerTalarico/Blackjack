<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Blackjack</title>
</head>
<body>



    <h1>
        Room: ${roomName}
    </h1>

    <ul id="playerList">

    </ul>


    <script>
        var ws = new WebSocket("ws://localhost:4567/roomJoin");
        var playerList = document.getElementById("playerList");

        var playerName = "${player.name! 'testPlayerName'}";
        var roomName = "${roomName}";



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
                let players = data.players;

                console.log(players);
                console.log(data);
                playerList.innerHTML = "";
                players.forEach(element => {
                    playerList.innerHTML += "<li> " + element.name + "</li>";
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



    </script>

</body>
</html>