import GameGallery from "../components/GameGallery";
import {createGame, fetchAllGames} from "../service/apiService";
import {useCallback, useEffect, useState} from "react";
import {Game} from "../service/model";
import Header from "../components/header/Header";
import {Button, Grid, TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import axios, { AxiosError } from "axios";

export default function MainPage(){


    const [game, setGame] = useState("")
    const [games, setGames] = useState<Array<Game>>([])
    const [errorMessageLoadGames, setErrorMessageLoadGames] = useState("")
    const [errorMessageCreateGame, setErrorMessageCreateGame] = useState("")
    const nav = useNavigate()


    useEffect(()=>{
        if (localStorage.getItem("jwt") === null || localStorage.getItem("jwt") === ""){
            nav("/")
        }
    },[nav])

    const logout = useCallback(() => {
        localStorage.clear();
        nav("/");
    }, [nav]);

    const apiAuthCheck = useCallback((err: Error | AxiosError) => {
        if (axios.isAxiosError(err) && err.response?.status === 403) {
            logout();

        }
    }, [logout])


    useEffect(()=>{
        fetchAllGames()
            .then((games: Array<Game>) => setGames(games))
            .then(()=> setErrorMessageLoadGames(""))
            .catch(err => apiAuthCheck(err))
    },[apiAuthCheck, game])

    function saveGame(){
        if (game.length > 0){
            createGame({gameName: game})
                .then(()=> setGame(""))
                .then(()=> apiAuthCheck)
                .then(()=> setErrorMessageCreateGame(""))
                .then(()=> fetchAllGames())
                .catch(()=> toast.warning("Game already in the list!"))
        } else {
            toast.warning("Add a game name!")
        }

    }


    const searchGames = games.sort((a,b) => a.gameName.localeCompare(b.gameName))
        .filter(g => g.gameName.toLowerCase().includes(game.toLowerCase()))
        .map(search => <Grid key={search.id}><GameGallery game={search}/></Grid>)




    return(
        <div>
            <Header/>
            <div>
                <Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <TextField required={true} type={"text"}  variant={"outlined"} color={"success"} label={"Add a game"} value={game} onChange={event => setGame(event.target.value)}/>
                    </Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <Button onClick={saveGame} variant={"contained"} color={"success"}>Add Game</Button>
                    </Grid>
                </Grid>
            </div>
            <Grid textAlign={"center"} fontSize={30} margin={2} color={"red"}>{errorMessageLoadGames}</Grid>
            <Grid textAlign={"center"} fontSize={30} margin={2} color={"red"}>{errorMessageCreateGame}</Grid>
            <div>
                {searchGames}
            </div>
        </div>

    )

}