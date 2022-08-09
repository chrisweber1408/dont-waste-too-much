import {Game} from "../service/model";
import {putToMyGames} from "../service/apiService";
import {useEffect, useState} from "react";
import {Box, Grid} from "@mui/material";
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
                <Grid margin={1} border={2} borderRadius={2} sx={{textOverflow: "auto"}}>
                    <Box component={"div"} onClick={() => nav("/communityGameInfo/" + props.game.id)} margin={0.5}
                         sx={{fontSize: 20, textOverflow: 'auto'}}>{props.game.gameName}</Box>
                    <Grid container>
                        <Grid xs={1.62}>
                        </Grid>
                        {roles.indexOf("admin") === 0 &&
                            <Grid margin={1} item xs={3} border={2} borderRadius={10} onClick={() => nav("/edit/" + props.game.id)}>
                            <Grid textAlign={"center"} color={"red"}>{props.game.approved === false && roles.indexOf("admin") === 0 &&
                            <EditIcon/>}</Grid>
                            <Grid textAlign={"center"}>{props.game.approved === true && roles.indexOf("admin") === 0 &&
                            <EditIcon/>}</Grid>
                            </Grid>
                        }
                        {roles.indexOf("admin") !== 0 &&
                            <Grid xs={4}>
                            </Grid>
                        }
                        <Grid xs={1.62}>
                        </Grid>
                        <Grid onClick={() => addToMyGames(props.game.id)} textAlign={"center"} border={2} item xs={3} borderRadius={10} margin={1}>
                            <AddIcon>Add</AddIcon></Grid>
                        <Grid xs={1.62}>
                        </Grid>
                    </Grid>
                </Grid>
            </div>
        </div>
    )
}