import {useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {editGame, getApprovedGame} from "../service/apiService";
import {game} from "../service/model";


export default function InfoPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as game)
    const [gameName, setGameName] = useState("")
    const [approved, setApproved] = useState()
    const [errorMessageId, setErrorMessageId] = useState("")


    const fetchGame = useCallback( ()=>{
        if(id){
            getApprovedGame(id)
                .then(response => response.data)
                .then(data => {
                    setGame(data)
                    setGameName(data.gameName)
                    setApproved(data.approved)
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
            editGame(id)
                .then(fetchGame)
    }

    return(
        <div>
            <div>
                <h3>Game Page</h3>
            </div>
            <div>
                {errorMessageId && <div>{errorMessageId}</div>}
                <div>{gameName}</div>
                <button onClick={switchStatus}>AdminSwitch</button>
            </div>
        </div>
    )
}