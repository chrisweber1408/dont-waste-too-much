import {useEffect, useState} from "react";
import {fetchAllMyGames} from "../service/apiService";
import {UserGameDTO} from "../service/model";
import {useNavigate} from "react-router-dom";
import Header from "./header/Header";
import {Grid} from "@mui/material";

export default function MyUserGames(){

    const [games, setGames] = useState<Array<UserGameDTO>>([])
    const [errorMessageLoadMyGames, setErrorMessageLoadMyGames] = useState("")
    const nav = useNavigate()

    useEffect(()=>{
        fetchAll()
    },[])

    const fetchAll = ()=>{
        fetchAllMyGames()
            .then((gameDataFromDb) => setGames(gameDataFromDb))
            .catch((error) => {
                if (error.response){
                    setErrorMessageLoadMyGames(error.response.data)
                }
            })
    }



    return(
        <div>
            <Header/>
            <div>
                <Grid textAlign={"center"} fontSize={30} margin={2} color={"red"}>{errorMessageLoadMyGames}</Grid>
            </div>
            <div>
                {games.map(game =>
                    <Grid border={2} borderRadius={2} margin={2} onClick={()=> nav("/infos/" + game.gameId)}>
                        <Grid item xs={12} textAlign={"center"} margin={1}>{game.gameName}</Grid>
                        <Grid container margin={1}>
                            <Grid item xs={6} textAlign={"center"}>Playtime: {game.playtime}</Grid>
                            <Grid item xs={6} textAlign={"center"}>spent Money: {game.spentMoneyCoins + game.spentMoneyGamePass + game.spentMoneyGame}</Grid>
                        </Grid>
                     </Grid>)}
            </div>
        </div>
    )
}