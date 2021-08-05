<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Blackjack</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="playerView stylesheet" href="../css/playerView.css">
</head>
<body>



    <h1>
        Room: ${roomName}
    </h1>

    <div id="content_container">
        <ul id="playerList">

        </ul>

        <div id="game_container"></div>
    </div>

    



    <script>
        var playerName = "${player.name! 'testPlayerName'}";
        var roomName = "${roomName}";
    </script>
    <script type="module" src="../js/roomWS.js"></script>
    <script type="module" src="../js/playerView.js"></script>



</body>
</html>