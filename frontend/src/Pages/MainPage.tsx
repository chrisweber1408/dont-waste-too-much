import GamesGallery from "../components/GamesGallery";
import {
    createAdminGame,
    createUserGame,
    fetchAllGames
} from "../service/apiService";
import {useEffect, useState} from "react";
import {Game} from "../service/model";

export default function MainPage(){


    const [userGame, setUserGame] = useState(localStorage.getItem("userGame") ?? "")
    const [adminGame, setAdminGame] = useState(localStorage.getItem("adminGame") ?? "")
    const [games, setGames] = useState<Array<Game>>([])
    const [errorMessageLoadGames, setErrorMessageLoadGames] = useState("")
    const [errorMessageCreateApprovedGame, setErrorMessageCreateApprovedGame] = useState("")
    const [errorMessageCreateUnapprovedGames, setErrorMessageCreateUnapprovedGames] = useState("")


    useEffect(()=>{
        localStorage.setItem("userGame", userGame)
    },[userGame])

    useEffect(()=>{
        localStorage.setItem("adminGame", adminGame)
    },[adminGame])

    useEffect(()=>{
        fetchAllApprovedGames()
    },[userGame, adminGame])

    const fetchAllApprovedGames = ()=>{
        fetchAllGames()
            .then((games: Array<Game>) => setGames(games))
            .then(()=> setErrorMessageLoadGames(""))
            .catch(()=> setErrorMessageLoadGames("The games could not be loaded"))
    }

    function saveAdminGame(){
        createAdminGame({gameName: adminGame})
            .then(()=> setAdminGame(""))
            .then(()=> setErrorMessageCreateApprovedGame(""))
            .catch(()=> setErrorMessageCreateApprovedGame("The game already exists"))
    }
    function saveUserGame(){
        createUserGame({gameName: userGame})
            .then(()=> setUserGame(""))
            .then(()=> setErrorMessageCreateUnapprovedGames(""))
            .catch(()=> setErrorMessageCreateUnapprovedGames("The game already exists"))
    }




    return(
        <div>
            <span>{errorMessageLoadGames}</span>
            <span>{errorMessageCreateApprovedGame}</span>
            <span>{errorMessageCreateUnapprovedGames}</span>
            <div>
                User
                <input type={"text"} placeholder={"Game to add"} value={userGame} onChange={event => setUserGame(event.target.value)}/>
                <button onClick={saveUserGame}>Add Game</button>
            </div>
            <div>
                Admin
                <input type={"text"} placeholder={"Game to add"} value={adminGame} onChange={event => setAdminGame(event.target.value)}/>
                <button onClick={saveAdminGame}>Add Game</button>
            </div>
            <div>
                <GamesGallery games={games}/>
            </div>
        </div>

    )

}