import {useNavigate, useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {editGame, getApprovedGame} from "../service/apiService";
import {game} from "../service/model";


export default function InfoPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as game)
    const [gameName, setGameName] = useState("")
    const [approved, setApproved] = useState()
    const [errorMessageId, setErrorMessageId] = useState("")
    const [errorMessageUnique, setErrorMessageUnique] = useState("")
    const nav = useNavigate()



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

    const saveChange = ()=>{
        editGame({
            "id": game.id,
            "gameName": gameName,
            "approved": approved
        })
            .then(fetchGame)
            .then(()=> nav("/"))
            .then(()=> setErrorMessageUnique(""))
            .catch(()=> setErrorMessageUnique("The game name already exists"))
    }

    const switchStatus = ()=>{
        if (approved == true){
            editGame({
                "id": game.id,
                "gameName": gameName,
                "approved": false
            }).then(fetchGame)
        } else {
            editGame({
                "id": game.id,
                "gameName": gameName,
                "approved": true
            }).then(fetchGame)
        }
    }

    return(
        <div>
            <div>
                <h3>Game Page</h3>
            </div>
            <div>
                {errorMessageUnique && <div>{errorMessageUnique}</div>}
                {errorMessageId && <div>{errorMessageId}</div>}
                <div>{gameName}</div>
                <div><input type={"text"} value={gameName} onChange={event => setGameName(event.target.value)}/></div>
                <button onClick={saveChange}>Add</button>
                <button onClick={switchStatus}>switch</button>
            </div>
        </div>
    )
}