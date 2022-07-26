import {Game} from "../service/model";
import {deleteGame, fetchAllGames, putToMyGames} from "../service/apiService";
import {useEffect, useState} from "react";
import {Grid} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';


interface GamesGalleryProps {
    games: Game
}

export default function GamesGallery(props: GamesGalleryProps) {

    const [errorMessageFav, setErrorMessageFav] = useState("")
    const [errorMessageDelete, setErrorMessageDelete] = useState("")
    const [roles, setRoles] = useState([""])

    useEffect(()=>{
        const decoded = window.atob(localStorage.getItem("jwt")!.split('.')[1])
        const decodeJWT = JSON.parse(decoded)
        setRoles(decodeJWT.roles)
    },[])


    function addToMyGames(id: string | undefined){
        if(id){
            putToMyGames(id)
                .catch(()=> setErrorMessageFav("Game already added to your list"))
        }
    }

    function deleteOneGame(id: string | undefined){
        if(id)
            deleteGame(id)
                .then(fetchAllGames)
                .catch(()=> setErrorMessageDelete("Game could not be deleted"))
    }

    return (
        <div>
            <div>
                {errorMessageFav}
                {errorMessageDelete}
            </div>
            <div>
                    <Grid margin={1} border={2} borderRadius={2}>
                        <Grid container>
                            <Grid item xs={8} margin={1} sx={{fontSize:20}}>{props.games.gameName}</Grid>
                            <Grid item xs={1} margin={1}>{roles.indexOf("admin") === 0 && <DeleteForeverIcon onClick={()=>deleteOneGame(props.games.id)}></DeleteForeverIcon>}</Grid>
                            <Grid item xs={1} margin={1}><AddIcon onClick={()=> addToMyGames(props.games.id)}>Add</AddIcon></Grid>
                        </Grid>
                    </Grid>
            </div>
        </div>
    )
}