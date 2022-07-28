import {useNavigate, useParams} from "react-router-dom";
import {FormEvent, useCallback, useEffect, useState} from "react";
import {
    switchGameStatus,
    getOneGame,
    updateGameStats,
    removeGameFromMyList
} from "../service/apiService";
import {UserGameDTO} from "../service/model";
import DeleteIcon from '@mui/icons-material/Delete';
import Header from "../components/header/Header";
import {Grid, Switch, TextField} from "@mui/material";
import PaidIcon from '@mui/icons-material/Paid';
import AccessTimeFilledIcon from '@mui/icons-material/AccessTimeFilled';
import {Add} from "@mui/icons-material";
import {SpentMoneyDoughnut} from "../components/charts/SpentMoneyDoughnut";
import {MoneyVsPlaytimeDoughnut} from "../components/charts/MoneyVsPlaytimeDoughnut";


export default function InfoPage() {

    const {id} = useParams()
    const [game, setGame] = useState({} as UserGameDTO)
    const [gameName, setGameName] = useState("")
    const [newPlaytime, setNewPlaytime] = useState(0)
    const [newSpentMoneyGamePass, setNewSpentMoneyGamePass] = useState(0)
    const [newSpentMoneyCoins, setNewSpentMoneyCoins] = useState(0)
    const [newSpentMoneyGame, setNewSpentMoneyGame] = useState(0)
    const [errorMessageId, setErrorMessageId] = useState("")
    const [roles, setRoles] = useState([""])
    const nav = useNavigate()


    const fetchGame = useCallback(() => {
        if (id) {
            getOneGame(id)
                .then(response => response.data)
                .then(data => {
                    setGame(data)
                    setGameName(data.gameName)
                })
                .then(() => setErrorMessageId(""))
                .catch(() => setErrorMessageId("The game could not be loaded"))
        }
    }, [id])

    useEffect(() => {
        fetchGame()
    }, [fetchGame])

    useEffect(() => {
        const decoded = window.atob(localStorage.getItem("jwt")!.split('.')[1])
        const decodeJWT = JSON.parse(decoded)
        setRoles(decodeJWT.roles)
    }, [])


    const switchStatus = () => {
        if (id)
            switchGameStatus(id)
                .then(fetchGame)
    }

    const submitForm = (event: FormEvent) => {
        event.preventDefault()
        updateGameStats({
            "gameId": game.gameId,
            "addedPlaytime": newPlaytime,
            "addedSpentMoneyGamePass": newSpentMoneyGamePass,
            "addedSpentMoneyCoins": newSpentMoneyCoins,
            "addedSpentMoneyGame": newSpentMoneyGame,
        })
            .then(() => setNewPlaytime(0))
            .then(() => setNewSpentMoneyGame(0))
            .then(() => setNewSpentMoneyCoins(0))
            .then(() => setNewSpentMoneyGamePass(0))
            .then(fetchGame)
    }

    const removeGame = () => {
        if (id)
            removeGameFromMyList(id)
                .then(() => nav("/main"))
    }

    return (
        <div>
            <Header/>
            <Grid textAlign={"center"}>
                <h1>{gameName}</h1>
            </Grid>
            <form onSubmit={submitForm}>
                {errorMessageId && <div>{errorMessageId}</div>}
                <Grid sx={{fontSize: 35}} textAlign={"center"}>
                    <AccessTimeFilledIcon/> {game.playtime} h
                </Grid>
                <Grid sx={{fontSize: 35}} textAlign={"center"}>
                    <PaidIcon/> {game.spentMoneyGame + game.spentMoneyGamePass + game.spentMoneyCoins} €
                </Grid>
                <Grid container spacing={2}>
                    <Grid item xs={6} textAlign={"center"} marginTop={1}>
                        <TextField label={"Time to add"} color={"success"} variant={"outlined"} type={"number"} size={"small"}
                                   value={newPlaytime} onChange={event => setNewPlaytime(parseFloat(event.target.value))}/>
                    </Grid>
                    <Grid item xs={6} textAlign={"center"} marginTop={1}>
                        <TextField label={"Game price"} color={"success"} variant={"outlined"} type={"number"} size={"small"}
                                   inputMode={"numeric"} value={newSpentMoneyGame} onChange={event => setNewSpentMoneyGame(parseFloat(event.target.value))}/>
                    </Grid>
                    <Grid item xs={6} textAlign={"center"} marginBottom={1}>
                        <TextField label={"Coins/Skins"} color={"success"} variant={"outlined"} type={"number"} size={"small"}
                                   inputMode={"numeric"} value={newSpentMoneyCoins} onChange={event => setNewSpentMoneyCoins(parseFloat(event.target.value))}/>
                    </Grid>
                    <Grid item xs={6} textAlign={"center"} marginBottom={1}>
                        <TextField label={"Game pass"} color={"success"} variant={"outlined"} type={"number"} size={"small"}
                                   inputMode={"numeric"} value={newSpentMoneyGamePass} onChange={event => setNewSpentMoneyGamePass(parseFloat(event.target.value))}/>
                    </Grid>
                </Grid>
                <Grid container>
                    <Grid item xs={6} textAlign={"center"}>
                        <Add type={"submit"} onClick={submitForm}>Add</Add>
                    </Grid>
                    <Grid item xs={6} textAlign={"center"}>
                        <DeleteIcon onClick={removeGame}>Delete</DeleteIcon>
                    </Grid>
                </Grid>
                {roles.indexOf("admin") === 0 && <Grid textAlign={"center"} sx={{fontSize: 20}}>
                    <Switch checked={game.approved} onClick={switchStatus}/>Approved status!
                </Grid>}
            </form>
            <Grid container>
                <Grid item xs={6} textAlign={"center"}>
                    <SpentMoneyDoughnut game={game}/>
                </Grid>
                <Grid item xs={6} textAlign={"center"}>
                    <MoneyVsPlaytimeDoughnut game={game}/>
                </Grid>
            </Grid>
        </div>
    )
}