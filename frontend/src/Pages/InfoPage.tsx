import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {editGame, getGame} from "../service/apiService";
import {Game} from "../service/model";

export default function InfoPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as Game)
    const [gameName, setGameName] = useState("")
    const [spentMoney, setSpentMoney] = useState(0)
    const [addSpentMoney, setAppSpentMoney] = useState(0)
    const [playtime, setPlaytime] = useState(0)
    const [addPlaytime, setAddPlaytime] = useState(0)
    const [approved, setApproved] = useState(false)

    const nav = useNavigate()

    useEffect(()=>{
        if(id){
            getGame(id)
                .then(response => response.data)
                .then(data => {
                    setGame(data)
                    setGameName(data.gameName)
                    setSpentMoney(data.spentMoney)
                    setPlaytime(data.playtime)
                    setApproved(data.approved)
                })
        }
    },[id])

    const saveChange = ()=>{
        const newPlaytime = playtime + addPlaytime
        const newSpentMoney = spentMoney + addSpentMoney
        editGame({
            "id": game.id,
            "gameName": gameName,
            "spentMoney": newSpentMoney,
            "playtime": newPlaytime,
            "approved": game.approved
        })
            .then(()=> nav("/"))
    }

    return(
        <div>
            <div>
                <h3>Game Page</h3>
            </div>
            <div>
                {approved && <div>{gameName}</div>}
                {!approved && <div><input type={"text"} value={gameName} onChange={event => setGameName(event.target.value)}/></div>}
                <div>Spent money: {spentMoney}<input type={"number"} onChange={event => setAppSpentMoney(parseFloat(event.target.value))}/></div>
                <div>Playtime: {playtime}<input type={"number"} onChange={event => setAddPlaytime(parseFloat(event.target.value))}/></div>
                <button onClick={saveChange}>Add</button>
            </div>
        </div>
    )
}