import {useEffect, useState} from "react";
import {fetchAllMyGames} from "../service/apiService";
import {GameData} from "../service/model";

export default function MyUserGames(){

    const [games, setGames] = useState<Array<GameData>>([])
    const [errorMessage, setErrorMessage] = useState("")

    useEffect(()=>{
        fetchAll()
    },[])

    const fetchAll = ()=>{
        fetchAllMyGames()
            .then((gameDataFromDb:Array<GameData>) => setGames(gameDataFromDb))
            .catch(()=> setErrorMessage("The games could not be loaded"))
    }



    return(
        <div>
            <div>
                {errorMessage && <div>{errorMessage}</div>}
            </div>
            <div>
                {games.map(game => <li>{game.playtime}</li>)}
                {games.map(game => <li>{game.money}</li>)}
            </div>
        </div>
    )
}