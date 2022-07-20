import {useEffect, useState} from "react";
import {deleteMyGame, fetchAllMyGames} from "../service/apiService";
import {GameData, UserGameDTO} from "../service/model";

export default function MyUserGames(){

    const [games, setGames] = useState<Array<UserGameDTO>>([])
    const [errorMessage, setErrorMessage] = useState("")
    const [gameId, setGameId] = useState("")

    useEffect(()=>{
        fetchAll()
    },[])

    const fetchAll = ()=>{
        fetchAllMyGames()
            .then((gameDataFromDb) => setGames(gameDataFromDb))
            .catch(()=> setErrorMessage("The games could not be loaded"))
    }



    return(
        <div>
            <div>
                {errorMessage && <div>{errorMessage}</div>}
            </div>
            <div>
                {games.map(game => <div>
                    Gamename: {game.gameName} Playtime: {game.gameData.playtime} spent Money: {game.gameData.money}
                </div>)}
            </div>
        </div>
    )
}