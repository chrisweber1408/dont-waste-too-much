import React, {useState, useEffect, FormEvent} from 'react';
import {createGame} from "./service/apiService";

export default function App() {

    const [game, setGame] = useState(localStorage.getItem("game") ?? "");

    function saveGame(event: FormEvent){
        event.preventDefault()
        createGame({gameName: game, playtime: 0, spentMoney: 0})
            .then(()=> setGame(""))
    }

    return(
        <div>
            <input type={"text"} placeholder={"Game to add"} value={game} onChange={event => setGame(event.target.value)}/>
            <button onClick={saveGame}>Add Game</button>
        </div>
    )
}
