import {useEffect, useState} from "react";
import {fetchAllMyGames} from "../service/apiService";
import {UserGameDTO} from "../service/model";
import {useNavigate} from "react-router-dom";

export default function MyUserGames(){

    const [games, setGames] = useState<Array<UserGameDTO>>([])
    const [errorMessage, setErrorMessage] = useState("")
    const nav = useNavigate()

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
                {games.map(game => <div onClick={()=> nav("/infos/" + game.gameId)}>
                    GameName: {game.gameName} Playtime: {game.playtime} spent Money: {game.spentMoney}
                </div>)}
            </div>
        </div>
    )
}