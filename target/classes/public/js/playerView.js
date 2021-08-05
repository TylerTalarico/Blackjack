
export function createPlayerViewElement (player) {

    const playerView = document.createElement("div")
    playerView.setAttribute("class", "player_view")
    playerView.setAttribute("id", "playerId_" + player.name)


    const cardView = document.createElement("div")
    cardView.setAttribute("class", "card_view")
    let hand = player.hand.cards;
    hand.forEach(card => {
        let card_name = card.id + "_" + card.suit
        let card_img = document.createElement("img")
        if (!card.flipped)
            card_img.setAttribute("src", "hidden_card");
        else
            card_img.setAttribute("src", card_name);
        card_img.setAttribute("alt", card_name)

        cardView.appendChild(card_img)

    });


    const nameView = document.createElement("div")
    nameView.setAttribute("class", "name_view")
    nameView.innerHTML = player.name;

    playerView.appendChild(cardView)
    playerView.appendChild(nameView)

    let gameView = document.getElementById("game_container")
    gameView.appendChild(playerView)


}





