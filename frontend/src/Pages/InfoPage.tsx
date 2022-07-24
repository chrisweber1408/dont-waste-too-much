import {useNavigate, useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {
    switchGameStatus,
    getOneGame,
    updateGameStats,
    removeGameFromMyList
} from "../service/apiService";
import {UserGameDTO} from "../service/model";
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import Header from "../components/header/Header";
import {Box, Grid, Switch, TextField} from "@mui/material";
import PaidIcon from '@mui/icons-material/Paid';
import AccessTimeFilledIcon from '@mui/icons-material/AccessTimeFilled';


export default function InfoPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as UserGameDTO)
    const [gameName, setGameName] = useState("")
    const [newPlaytime, setNewPlaytime] = useState(0)
    const [newSpentMoney, setNewSpentMoney] = useState(0)
    const [errorMessageId, setErrorMessageId] = useState("")
    const [roles, setRoles] = useState([""])
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

    useEffect(()=>{
        const decoded = window.atob(localStorage.getItem("jwt")!.split('.')[1])
        const decodeJWT = JSON.parse(decoded)
        setRoles(decodeJWT.roles)
    },[])


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
            <Header/>
            <Grid textAlign={"center"}>
                <h1>{gameName}</h1>
            </Grid>
            <div>
                {errorMessageId && <div>{errorMessageId}</div>}
                <Grid sx={{fontSize: 35}} textAlign={"center"} >
                    <PaidIcon /> {game.spentMoney} €
                </Grid>
                <Grid sx={{fontSize: 35}} textAlign={"center"} >
                    <AccessTimeFilledIcon /> {game.playtime} h
                </Grid>
                <Grid textAlign={"center"}>
                    <input placeholder={"Add spent Money"} type={"number"} inputMode={"numeric"} onChange={event => setNewSpentMoney(event.target.valueAsNumber)} />
                </Grid>
                <Grid textAlign={"center"}>
                    <input placeholder={"Add time played"} type={"number"} inputMode={"numeric"} onChange={event => setNewPlaytime(event.target.valueAsNumber)} />
                </Grid>
                <Grid container>
                    <Grid item xs={6} textAlign={"center"}>
                        <AddIcon onClick={updateGame} >Add</AddIcon>
                    </Grid>
                    <Grid item xs={6} textAlign={"center"}>
                        <DeleteIcon onClick={removeGame}>Delete</DeleteIcon>
                    </Grid>
                </Grid>
                {roles.indexOf("admin") === 0 && <Grid textAlign={"center"} sx={{fontSize: 20}}>
                     <Switch checked={game.approved} onClick={switchStatus}/>Approved status!
                </Grid>}
            </div>
        </div>
    )
}