import {useCallback, useEffect, useState} from "react";
import {getOneCommunityGame} from "../service/apiService";
import {useNavigate, useParams} from "react-router-dom";
import {CommunityStatsDTO} from "../service/model";
import Header from "../components/header/Header";
import {Grid} from "@mui/material";
import {TotalCommunityMoneyVsPlaytimeDoughnut} from "../components/charts/TotalCommunityMoneyVsPlaytimeDoughnut";
import {TotalCommunitySpentMoneyDoughnut} from "../components/charts/TotalCommunitySpentMoneyDoughnut";
import {AverageCommunitySpentMoneyDoughnut} from "../components/charts/AverageCommunitySpentMoneyDoughnut";
import {AverageCommunityMoneyVsPlaytimeDoughnut} from "../components/charts/AverageCommunityMoneyVsPlaytimeDoughnut";
import {toast} from "react-toastify";

export default function CommunityGameInfoPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as CommunityStatsDTO)
    const nav = useNavigate()

    useEffect(()=>{
        if (localStorage.getItem("jwt") === null || localStorage.getItem("jwt") === ""){
            nav("/")
        }
    },[nav])

    const fetchGame = useCallback(() => {
        if (id) {
            getOneCommunityGame(id)
                .then(response => response.data)
                .then(data => {
                    setGame(data)
                })
                .catch(()=> toast("No game stats!"))
        }
    }, [id])

    useEffect(() => {
        fetchGame()
    }, [fetchGame])


    return(
        <div>
            <Header/>
            <Grid textAlign={"center"}>
                <h1>{game.gameName}</h1>
            </Grid>
                <Grid container>
                    <Grid item xs={6} textAlign={"center"}>
                        <TotalCommunitySpentMoneyDoughnut game={game}/>
                    </Grid>
                    <Grid item xs={6} textAlign={"center"}>
                        <TotalCommunityMoneyVsPlaytimeDoughnut game={game}/>
                    </Grid>
                </Grid>
                <Grid container>
                    <Grid item xs={12} textAlign={"center"}>
                        <AverageCommunitySpentMoneyDoughnut game={game}/>
                    </Grid>
                    <Grid item xs={12} textAlign={"center"}>
                        <AverageCommunityMoneyVsPlaytimeDoughnut game={game}/>
                    </Grid>
                </Grid>
        </div>
    )
}