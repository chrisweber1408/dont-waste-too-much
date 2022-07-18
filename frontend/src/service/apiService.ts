import {game, LoginData, LoginResponse, MyUserCreationData} from "./model";
import axios, {AxiosResponse} from "axios";

export function createAdminGame(game: game){
    return axios.post("/api/game/admin", game,{
        headers:{
            Authorization: "Bearer " + localStorage.getItem("jwt")
        }
    })
}

export function createUserGame(game: game){
    return axios.post("/api/game/user", game, {
        headers:{
            Authorization: "Bearer " + localStorage.getItem("jwt")
        }
    })
}

export function fetchAllGames(){
    return axios.get("/api/game", {
        headers:{
            Authorization: "Bearer " + localStorage.getItem("jwt")
        }
    })
        .then((response: AxiosResponse<Array<game>>) => response.data)
}

export function getApprovedGame(gameId: string){
    return axios.get("/api/game/" + gameId, {
        headers:{
            Authorization: "Bearer " + localStorage.getItem("jwt")
        }
    })
}

export function editGame(gameId: string){
    return axios.put("/api/game/" + gameId, gameId, {
        headers:{
            Authorization: "Bearer " + localStorage.getItem("jwt")
        }
    })
}



//----------- Register and Login -----------//

export function registerUser(myUserCreationData: MyUserCreationData){
    return axios.post(`/api/users`, myUserCreationData)
}

export function sendLogin(loginData: LoginData){
    return axios.post(`/api/login`, loginData)
        .then((response:AxiosResponse<LoginResponse>) => response.data)
}
