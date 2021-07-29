<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Blackjack</title>
</head>
<body>

    <ul id="playerList">

    </ul>


    <script>
        var ws = new WebSocket("ws://localhost:4567/room" + ${roomName});
        var playerList = document.getElementByID("playerList").innerHTML;

        ws.onopen = function (event) {
            console.log("Websocket to Rooms is open");
        }

        ws.onclose = function (event) {
            console.log("Websocket to Rooms is closed");
        }

        ws.onmessage = function (event) {
            let data = JSON.parse(event.data);
            processMessage(data);

        }

        function processMessage(data) {
            let message = data.message;
            if (message === "playerConnection") {
                let players = data.players;
                players.array.forEach(element => {
                    playerList += "<li> " + element + "</li>";
                });
            }
        }


    </script>

</body>
</html>