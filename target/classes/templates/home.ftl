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

    <form id="createRoom" action="/createRoom" method="post">
        <input placeholder="Room Name" name="roomName"></input>
        <input type="number" placeholder="Player Cap" name="playerCap"></input>
        <input type="number" placeholder="Points to Win" name="pointCap"></input>
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
    <ul id="roomList">

    </ul>


</body>
<script >
    var ws = new WebSocket("ws://localhost:4567/roomList");
    var roomList = document.getElementById("roomList");



    ws.onopen = function (event) {
        console.log("Websocket to Rooms is open");

    }

    ws.onclose = function (event) {
        console.log("Websocket to Rooms is closed");
    }

    ws.onmessage = function (event) {
        processMessage(JSON.parse(event.data));
        console.log("Received message");
    }

    function processMessage(data) {
        let rooms = data;
        roomList.innerHTML = "";
        rooms.forEach(element => {
        roomList.innerHTML += "<li> <a href='/room?roomName=" + element + "'>" + element + "</a> </li>";
        });
    }


</script>
</html>