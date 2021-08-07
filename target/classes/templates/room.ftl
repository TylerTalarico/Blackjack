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

        <div id="game_container">
            <div id="action_bar">
                <button disabled id="hit_btn">Hit</button>
                <button disabled id="stand_btn">Stand</button>
            </div>
        </div>
    </div>

    <button hidden disabled id="start_btn" >Start Game</button>


    <script>

        window.playerName = "${player.name! 'testPlayerName'}"
        window.roomName = "${roomName}"
        window.hostName = "${hostPlayer.name!}"

        if (playerName === hostName) {
            document.getElementById("start_btn").removeAttribute("hidden")
            document.getElementById("start_btn").removeAttribute("disabled")
        }
        
    </script>
    <script type="module" src="../js/roomWS.js"></script>
    <script type="module" src="../js/playerView.js"></script>
    



</body>
</html>