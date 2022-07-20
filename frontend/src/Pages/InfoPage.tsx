import {useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {editGame, getOneGame} from "../service/apiService";


export default function InfoPage(){

    const {id} = useParams()
    const [gameName, setGameName] = useState(0)
    const [errorMessageId, setErrorMessageId] = useState("")


    const fetchGame = useCallback( ()=>{
        if(id){
            getOneGame(id)
                .then(response => response.data)
                .then(data => {
                    setGameName(data.gameName)
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
                <div>
                    <input type={"number"}/><button>Add</button>
                </div>
                <button onClick={switchStatus}>AdminSwitch</button>
            </div>
        </div>
    )
}