import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getGame} from "../service/apiService";
import {Game} from "../service/model";

export default function EditPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as Game)
    const [gameName, setGameName] = useState("")
    const [spentMoney, setSpentMoney] = useState("")
    const [playtime, setPlaytime] = useState("")
    const [approved, setApproved] = useState(false)

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

    return(
        <div>
            <div>
                <h3>Game Page</h3>
            </div>
            <div>
                {approved && <div>{gameName}</div>}
                {!approved && <span><input type={"text"} value={gameName}/></span>}
                <span><input type={"number"} value={spentMoney}/></span>
                <span><input type={"number"} value={playtime}/></span>
            </div>
        </div>
    )
}