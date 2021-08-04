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
        var playerName = "${player.name! 'testPlayerName'}";
        var roomName = "${roomName}";
    </script>
    <script src="/js/roomWS.js"></script>

    <script>
        console.log(players);
    </script>

</body>
</html>