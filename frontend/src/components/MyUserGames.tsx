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
                    GameName: {game.gameName} Playtime: {game.playtime} spent Money: {game.spentMoney}
                </div>)}
            </div>
        </div>
    )
}