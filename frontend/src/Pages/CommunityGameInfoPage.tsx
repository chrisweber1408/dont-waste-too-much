import {useCallback, useEffect, useState} from "react";
import {getOneCommunityGame} from "../service/apiService";
import {useParams} from "react-router-dom";
import {CommunityStatsDTO} from "../service/model";
import Header from "../components/header/Header";

export default function CommunityGameInfoPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as CommunityStatsDTO)
    const [errorMessage, setErrorMessage] = useState("")

    const fetchGame = useCallback(() => {
        if (id) {
            getOneCommunityGame(id)
                .then(response => response.data)
                .then(data => {
                    setGame(data)
                })
                .then(() => setErrorMessage(""))
                .catch((error) => {
                    if (error.response){
                        setErrorMessage(error.response.data)
                    }
                })
        }
    }, [id])

    useEffect(() => {
        fetchGame()
    }, [fetchGame])

    return(
        <div>
            <Header/>
            <div>
                {game.gameName}
            </div>
        </div>
    )
}