import React, {useState, FormEvent, useEffect} from 'react';
import {createGame, fetchAllGames} from "./service/apiService";
import {Game} from "./service/model";
import GamesGallery from "./components/GamesGallery";

export default function App() {

    const [userGame, setUserGame] = useState(localStorage.getItem("userGame") ?? "")
    const [adminGame, setAdminGame] = useState(localStorage.getItem("adminGame") ?? "")
    const [games, setGames] = useState<Array<Game>>([])
    const [errorMessage, setErrorMassage] = useState("")

    useEffect(()=>{
        localStorage.setItem("userGame", userGame)
    },[userGame])

    useEffect(()=>{
        localStorage.setItem("adminGame", adminGame)
    },[adminGame])

    useEffect(()=>{
        fetchAll()
    },[userGame, adminGame])

    const fetchAll = ()=>{
        fetchAllGames()
            .then((gamesFromDb: Array<Game>) => setGames(gamesFromDb))
            .catch(()=> setErrorMassage("The games could not be loaded"))
        // showtime of errorMessage
    }

    function saveGameFromUser(event: FormEvent){
        event.preventDefault()
        createGame({gameName: userGame, playtime: 0, spentMoney: 0, approved: false})
            .then(()=> setUserGame(""))
    }
    function saveGameFromAdmin(event: FormEvent){
        event.preventDefault()
        createGame({gameName: adminGame, playtime: 0, spentMoney: 0, approved: true})
            .then(()=> setAdminGame(""))
    }



    return(
        <div>
            <span>{errorMessage}</span>
            <div>
                User
                <input type={"text"} placeholder={"Game to add"} value={userGame} onChange={event => setUserGame(event.target.value)}/>
                <button onClick={saveGameFromUser}>Add Game</button>
            </div>
            <div>
                Admin
                <input type={"text"} placeholder={"Game to add"} value={adminGame} onChange={event => setAdminGame(event.target.value)}/>
                <button onClick={saveGameFromAdmin}>Add Game</button>
            </div>
            <div>
                <GamesGallery games={games}/>
            </div>
        </div>

    )
}
