import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {editUnapprovedGame, getUnapprovedGame} from "../service/apiService";
import {UnapprovedGame} from "../service/model";


export default function UnapprovedInfoPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as UnapprovedGame)
    const [gameName, setGameName] = useState("")
    const [spentMoney, setSpentMoney] = useState(0)
    const [addSpentMoney, setAddSpentMoney] = useState(0)
    const [playtime, setPlaytime] = useState(0)
    const [addPlaytime, setAddPlaytime] = useState(0)
    const [approved, setApproved] = useState(false)
    const [errorMessageId, setErrorMessageId] = useState("")
    const [errorMessageUnique, setErrorMessageUnique] = useState("")
    const newPlaytime = playtime + addPlaytime
    const newSpentMoney = spentMoney + addSpentMoney
    const nav = useNavigate()

    useEffect(()=>{
        fetchGame()
    },[id])

    const fetchGame = ()=>{
        if(id){
            getUnapprovedGame(id)
                .then(response => response.data)
                .then(data => {
                    setGame(data)
                    setGameName(data.gameName)
                    setSpentMoney(data.spentMoney)
                    setPlaytime(data.playtime)
                    setApproved(data.approved)
                })
                .then(()=> setErrorMessageId(""))
                .catch(()=> setErrorMessageId("The game could not be loaded"))
        }
    }

    const saveChange = ()=>{
        editUnapprovedGame({
            "id": game.id,
            "gameName": gameName,
            "spentMoney": newSpentMoney,
            "playtime": newPlaytime,
            "approved": game.approved
        })
            .then(fetchGame)
            .then(()=> nav("/"))
            .then(()=> setErrorMessageUnique(""))
            .catch(()=> setErrorMessageUnique("The game name already exists"))
    }

    return(
        <div>
            <div>
                <h3>Game Page</h3>
            </div>
            <div>
                {errorMessageUnique && <div>{errorMessageUnique}</div>}
                {errorMessageId && <div>{errorMessageId}</div>}
                {approved && <div>{gameName}</div>}
                {!approved && <div><input type={"text"} value={gameName} onChange={event => setGameName(event.target.value)}/></div>}
                <div>Spent money: {spentMoney}<input type={"number"} onChange={event => setAddSpentMoney(parseFloat(event.target.value))}/></div>
                <div>Playtime: {playtime}<input type={"number"} onChange={event => setAddPlaytime(parseFloat(event.target.value))}/></div>
                <button onClick={saveChange}>Add</button>
            </div>
        </div>
    )
}