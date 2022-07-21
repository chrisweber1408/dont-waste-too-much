import {useNavigate, useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {
    switchGameStatus,
    getOneGame,
    updateGameStats,
    postToMyGames,
    removeGameFromMyList
} from "../service/apiService";
import {UserGameDTO} from "../service/model";


export default function InfoPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as UserGameDTO)
    const [gameName, setGameName] = useState("")
    const [newPlaytime, setNewPlaytime] = useState(0)
    const [newSpentMoney, setNewSpentMoney] = useState(0)
    const [errorMessageId, setErrorMessageId] = useState("")
    const nav = useNavigate()


    const fetchGame = useCallback( ()=>{
        if(id){
            getOneGame(id)
                .then(response => response.data)
                .then(data => {
                    setGame(data)
                    setGameName(data.gameName)
                    setNewPlaytime(data.playtime)
                    setNewSpentMoney(data.spentMoney)
                })
                .then(()=> setErrorMessageId(""))
                .catch(()=> setErrorMessageId("The game could not be loaded"))
        }
    },[id])

    useEffect(()=>{
        fetchGame()
    },[fetchGame])


    const switchStatus = ()=>{
        if (id)
            switchGameStatus(id)
                .then(fetchGame)
    }

    const updateGame = ()=>{
        updateGameStats({
            "username": game.username,
            "gameName": game.gameName,
            "playtime": game.playtime + newPlaytime,
            "spentMoney": game.spentMoney + newSpentMoney,
            "gameId": game.gameId,
            "approved": game.approved
        })
            .then(()=> setNewPlaytime(0))
            .then(()=> setNewSpentMoney(0))
            .then(fetchGame)
    }

    const removeGame = ()=>{
        if(id)
            removeGameFromMyList(id)
                .then(()=> nav("/main"))
    }


    return(
        <div>
            <div>
                <h3>Game Page</h3>
            </div>
            <div>
                {errorMessageId && <div>{errorMessageId}</div>}
                <div>{gameName}</div>
                <div>Spent money: {game.spentMoney}</div>
                <div>Playtime: {game.playtime}</div>
                <div>
                    New spent money<input type={"number"} onChange={event => setNewSpentMoney(event.target.valueAsNumber)} />
                </div>
                <div>
                    New playtime<input type={"number"} onChange={event => setNewPlaytime(event.target.valueAsNumber)} />
                </div>
                <div>
                    <button onClick={updateGame} >Add</button>
                </div>
                <div>
                    <button onClick={removeGame} >Delete</button>
                </div>
                <button onClick={switchStatus}>AdminSwitch</button>
            </div>
        </div>
    )
}