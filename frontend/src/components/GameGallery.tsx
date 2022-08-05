import {Game} from "../service/model";
import {putToMyGames} from "../service/apiService";
import {useEffect, useState} from "react";
import {Grid} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import {useNavigate} from "react-router-dom";
import EditIcon from '@mui/icons-material/Edit';
import {toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css"

interface GamesGalleryProps {
    game: Game
}

export default function GameGallery(props: GamesGalleryProps) {

    const [roles, setRoles] = useState([""])
    const nav = useNavigate()

    useEffect(() => {
        const decoded = window.atob(localStorage.getItem("jwt")!.split('.')[1])
        const decodeJWT = JSON.parse(decoded)
        setRoles(decodeJWT.roles)
    }, [])



    function addToMyGames(id: string | undefined) {
        if (id) {
            putToMyGames(id)
                .then(()=> toast.success("Game added to your list."))
                .catch(()=> toast.warning("Game already added to your games!"))
        }
    }

    return (
        <div>
            <div>
                <Grid margin={1} border={2} borderRadius={2}>
                    <Grid container>
                        <Grid onClick={() => nav("/communityGameInfo/" + props.game.id)} item xs={9} margin={1}
                              sx={{fontSize: 20}}>{props.game.gameName}</Grid>
                        <Grid item xs={1} onClick={() => nav("/edit/" + props.game.id)}>
                            <Grid color={"red"} item xs={0.5}
                                  margin={1}>{props.game.approved === false && roles.indexOf("admin") === 0 &&
                                <EditIcon/>}</Grid>
                            <Grid item xs={0.5}
                                  margin={1}>{props.game.approved === true && roles.indexOf("admin") === 0 &&
                                <EditIcon/>}</Grid>
                        </Grid>
                        <Grid item xs={0.8} margin={1}><AddIcon
                            onClick={() => addToMyGames(props.game.id)}>Add</AddIcon></Grid>
                    </Grid>
                </Grid>
            </div>
        </div>
    )
}