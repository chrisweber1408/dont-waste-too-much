import GamesGallery from "../components/GamesGallery";
import {createGame, fetchAllGames} from "../service/apiService";
import {useEffect, useState} from "react";
import {Game} from "../service/model";
import Header from "../components/header/Header";
import {Button, Grid, TextField} from "@mui/material";

export default function MainPage(){


    const [game, setGame] = useState(localStorage.getItem("game") ?? "")
    const [games, setGames] = useState<Array<Game>>([])
    const [errorMessageLoadGames, setErrorMessageLoadGames] = useState("")
    const [errorMessageCreateGame, setErrorMessageCreateGame] = useState("")


    useEffect(()=>{
        localStorage.setItem("game", game)
    },[game])

    useEffect(()=>{
        fetchAllApprovedGames()
    },[game])

    const fetchAllApprovedGames = ()=>{
        fetchAllGames()
            .then((games: Array<Game>) => setGames(games))
            .then(()=> setErrorMessageLoadGames(""))
            .catch(()=> setErrorMessageLoadGames("The games could not be loaded"))
    }

    function saveGame(){
        createGame({gameName: game})
            .then(()=> setGame(""))
            .then(()=> setErrorMessageCreateGame(""))
            .catch(()=> setErrorMessageCreateGame("The game already exists"))
    }




    return(
        <div>
            <Header/>
            <span>{errorMessageLoadGames}</span>
            <span>{errorMessageCreateGame}</span>
            <div>
                <form onSubmit={saveGame}>
                    <Grid textAlign={"center"} margin={1}>
                        <TextField type={"text"}  variant={"outlined"} color={"success"} label={"Add a game"} value={game} onChange={event => setGame(event.target.value)}/>
                    </Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <Button variant={"contained"} type={"submit"} color={"success"}>Add Game</Button>
                    </Grid>
                </form>
            </div>
            <div>
                <GamesGallery games={games}/>
            </div>
        </div>

    )

}