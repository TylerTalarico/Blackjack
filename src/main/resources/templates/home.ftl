<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blackjack | Home</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Hammersmith+One&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/home.css">
    <link rel="stylesheet" href="../css/roomList.css">
    <script src="https://kit.fontawesome.com/83fb009173.js" crossorigin="anonymous"></script>
</head>
<body>
    


    <div id="create-room-form">
        <button id="close-btn"> <i class="fas fa-times"></i></button>
        <form action="/createRoom" method="post">
            <input class="room-name-form" placeholder="Room Name" name="roomName"></input>
            <input class="number-form" type="number" placeholder="Player Cap" name="playerCap" min="2" max="6"></input>
            <input class="number-form" type="number" placeholder="Points to Win" name="pointCap" min="1" max="11"></input>
            <button type= "submit" id="createRoomBtn">Create</button>
        </form>
    </div>

    <div id="static-content">
        <div id="header">
            <h1 id="welcome-message">Welcome to Blackjack</h1>
            <#if player??>
                <h1>Hello ${player.name!}</h1>
            <#else>
                <div id="signin-box">
                    <form action="/signin" method="post">
                        <input placeholder="Enter Username" id="username" name="username"> </input>
                        <button type= "submit" id="signInBtn">Sign In</button>
                    </form>
                </div>
            </#if>
        </div>
        
        
        

        <h1 class="room-heading">Rooms: <button id="create-room-btn">Create Room</button></h1>

        <ul id="room-list">

        </ul>
    </div>


</body>
<script>
    var createRoomBtn = document.getElementById("create-room-btn")
    createRoomBtn.addEventListener("click", showCreateRoomForm)

    var createRoomForm = document.getElementById("create-room-form")
    var staticContent = document.getElementById("static-content")

    var exitBtn = document.getElementById("close-btn")
    exitBtn.addEventListener("click", exitCreateRoomForm)

    function showCreateRoomForm() {
        createRoomForm.style.transform = "translateY(100%)"
        createRoomForm.style.visibility = "visible"
        staticContent.style.opacity = ".1"
        createRoomForm.style.opacity = "1"
    }

    function exitCreateRoomForm() {
        createRoomForm.style.transform = "translateY(-100%)"
        createRoomForm.style.opacity = "0"

        setTimeout(function() {
            createRoomForm.visibility = "hidden"
            staticContent.style.opacity = "1"
        }, 500)

    }

    window.onbeforeunload = function() {
        xhtml = new XMLHttpRequest()
        xhtml.open("POST", "/signOut", false)
        xhtml.send();
    }
</script>
<script type="module" src="../js/homeWS.js"></script>
<script type="module" src="../js/roomListView.js"></script>
</html>