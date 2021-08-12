<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Blackjack</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Hammersmith+One&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="playerView stylesheet" href="../css/playerView.css">
</head>
<body>



    <h1>
        Room: ${roomName}
    </h1>

    <div id="content_container">
        <div id="notifications">
        </div>
        <div id="game_container">
            <div id="action_bar">
                <button hidden disabled id="start_btn" >Start Game</button>
                <button id="hit_btn">Hit</button>
                <button id="stand_btn">Stand</button>
            </div>
        </div>
    </div>

    


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