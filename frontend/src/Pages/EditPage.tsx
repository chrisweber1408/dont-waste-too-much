import Header from "../components/header/Header";
import {useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {deleteGame, editOneGame, fetchAllGames, getOneGameToEdit, switchGameStatus} from "../service/apiService";
import {Button, Grid, Switch, TextField} from "@mui/material";
import {Game} from "../service/model";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";

export default function EditPage(){

    const {id} = useParams()
    const [game, setGame] = useState({} as Game)
    const [gameName, setGameName] = useState("")
    const [errorMessage, setErrorMessage] = useState("")
    const [roles, setRoles] = useState([""])

    const fetchGame = useCallback(() => {
        if (id) {
            getOneGameToEdit(id)
                .then(response => response.data)
                .then(data => {
                    setGame(data)
                    setGameName(data.gameName)
                })
                .then(() => setErrorMessage(""))
                .catch((error) => {
                    if (error.response){
                        setErrorMessage(error.response.data)
                    }
                })
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
                .then(() => setErrorMessage(""))
                .catch((error) => {
                    if (error.response){
                        setErrorMessage(error.response.data)
                    }
                })
    }

    function deleteOneGame(id: string | undefined) {
        if (id) {
            deleteGame(id)
                .then(fetchAllGames)
                .catch((error) => {
                    if (error.response) {
                        setErrorMessage(error.response.data)
                    }
                })
        }
    }

    const editGame = ()=>{
        editOneGame({
            "id": game.id,
            "gameName": gameName,
            "approved": game.approved
        })
            .then()
    }

    return(
        <div>
            <Header/>
            <Grid textAlign={"center"}>
                <h1>Edit Page</h1>
                <h2>{game.gameName}</h2>
                {roles.indexOf("admin") === 0 && <Grid textAlign={"center"} sx={{fontSize: 20}}>
                    <Switch checked={game.approved} onClick={switchStatus}/>Approved status!
                </Grid>}
                <Grid textAlign={"center"} fontSize={30} margin={2} color={"red"}>{errorMessage}</Grid>
                <Grid>
                    <TextField type={"text"}  variant={"outlined"} color={"success"} value={gameName} onChange={event => setGameName(event.target.value)}/>
                </Grid>
                <Grid margin={1}>
                    <Button onClick={()=> editGame} variant={"contained"} color={"success"}>Save name</Button>
                </Grid>
                <Grid item xs={0.5} margin={1}>{roles.indexOf("admin") === 0 &&
                    <DeleteForeverIcon onClick={() => deleteOneGame(game.id)}></DeleteForeverIcon>}</Grid>
            </Grid>
        </div>
    )
}