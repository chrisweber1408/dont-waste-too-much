import {game, LoginResponse} from "./model";
import axios, {AxiosResponse} from "axios";

export function createAdminGame(game: game){
    return axios.post("/api/game/admin", game,{
        headers:{
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    })
}

export function createUserGame(game: game){
    return axios.post("/api/game/user", game, {
        headers:{
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    })
}

export function fetchAllGames(){
    return axios.get("/api/game", {
        headers:{
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    })
        .then((response: AxiosResponse<Array<game>>) => response.data)
}

export function getApprovedGame(gameId: string){
    return axios.get("/api/game/" + gameId, {
        headers:{
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    })
}

export function editGame(gameId: string){
    return axios.put("/api/game/" + gameId, gameId, {
        headers:{
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    })
}



//----------- Register and Login -----------//

export function registerUser(username: string, password: string){
    return axios.post(`/api/user`,{
        username,
        password
    })
}

export function sendLogin(username: string, password: string){
    return axios.post(`/api/login`,{
        username,
        password
    })
        .then((response:AxiosResponse<LoginResponse>) => response.data)
}
