import {useEffect, useState} from "react";
import {fetchAllMyGames} from "../service/apiService";
import {UserGameDTO} from "../service/model";
import {useNavigate} from "react-router-dom";
import Header from "./header/Header";
import {Grid} from "@mui/material";
import {toast} from "react-toastify";

export default function MyUserGames(){

    const [games, setGames] = useState<Array<UserGameDTO>>([])
    const nav = useNavigate()

    useEffect(()=>{
        if (localStorage.getItem("jwt") === null || localStorage.getItem("jwt") === ""){
            nav("/")
        }
    },[nav])

    useEffect(()=>{
        fetchAll()
    },[])


    const fetchAll = ()=>{
        if (localStorage.getItem("jwt") !== null && localStorage.getItem("jwt") !== ""){
            fetchAllMyGames()
                .then((gameDataFromDb) => setGames(gameDataFromDb))
                .catch(()=>toast.warning("Add some games!"))
        }
    }



    return(
        <div>
            <Header/>
            <div>
                {games.sort((a,b) => a.gameName.localeCompare(b.gameName))
                    .map(game =>
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