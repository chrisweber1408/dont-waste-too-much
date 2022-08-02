import GameGallery from "../components/GameGallery";
import {createGame, fetchAllGames} from "../service/apiService";
import {useEffect, useState} from "react";
import {Game} from "../service/model";
import Header from "../components/header/Header";
import {Button, Grid, TextField} from "@mui/material";

export default function MainPage(){


    const [game, setGame] = useState("")
    const [games, setGames] = useState<Array<Game>>([])
    const [errorMessageLoadGames, setErrorMessageLoadGames] = useState("")
    const [errorMessageCreateGame, setErrorMessageCreateGame] = useState("")

    useEffect(()=>{
        fetchAllCommunityGames()
    },[game])

    const fetchAllCommunityGames = ()=>{
        fetchAllGames()
            .then((games: Array<Game>) => setGames(games))
            .then(()=> setErrorMessageLoadGames(""))
            .catch((error) => {
                if (error.response){
                    setErrorMessageLoadGames(error.response.data)
                }
            })
    }

    function saveGame(){
        createGame({gameName: game})
            .then(()=> setGame(""))
            .then(()=> setErrorMessageCreateGame(""))
            .catch((error) => {
                if (error.response){
                    setErrorMessageCreateGame(error.response.data)
                }
            })
    }


    const searchGames = games
        .filter(g => g.gameName.toLowerCase().includes(game.toLowerCase()))
        .map(search => <Grid key={search.id}><GameGallery game={search}/></Grid>)




    return(
        <div>
            <Header/>
            <div>
                <Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <TextField type={"text"}  variant={"outlined"} color={"success"} label={"Add a game"} value={game} onChange={event => setGame(event.target.value)}/>
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