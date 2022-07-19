import {Game, LoginData, LoginResponse, MyUserCreationData} from "./model";
import axios, {AxiosResponse} from "axios";

let requestConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem("jwt")}`
    }
}

export function createAdminGame(game: Game){
    return axios.post("/api/game/admin", game, requestConfig)
}

export function createUserGame(game: Game){
    return axios.post("/api/game/user", game, requestConfig)
}

export function fetchAllGames(){
    return axios.get("/api/game", requestConfig)
        .then((response: AxiosResponse<Array<Game>>) => response.data)
}

export function getApprovedGame(gameId: string){
    return axios.get("/api/game/" + gameId, requestConfig)
}

export function editGame(gameId: string){
    return axios.put("/api/game/" + gameId, gameId, requestConfig)
}



//----------- Register and Login -----------//

export function registerUser(myUserCreationData: MyUserCreationData){
    return axios.post(`/api/users`, myUserCreationData)
}

export function sendLogin(loginData: LoginData){
    return axios.post(`/api/login`, loginData)
        .then((response:AxiosResponse<LoginResponse>) => response.data)
}
