<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blackjack | Home</title>
</head>
<body>
    <h1>Welcome to Blackjack</h1>
    <h2>There are <span id='numRooms'></span> rooms</h2>

    <form id="createRoom">
        <input placeholder="Room Name"></input>
        <button type= "submit" id="createRoomBtn">Create</button>
    </form>

    <#if player??>
        <p>
            Hello, ${player.name! 'testName'}!
        </p>

    <#else>
        <form action="/signin" method="post">
             <input placeholder="Enter Username" id="username" name="username"> </input>
             <button type= "submit" id="signInBtn">Sign In</button>
        </form>
    </#if>


</body>
<script >
    var ws = new WebSocket("ws://localhost:4567/rooms");
    var numRooms = document.getElementById("numRooms");


    ws.onopen = function (event) {
        console.log("Websocket to Rooms is open");
    }

    ws.onclose = function (event) {
        console.log("Websocket to Rooms is closed");
    }

    ws.onmessage = function (event) {
        numRooms.innerHTML = event.data;
    }


</script>
</html>