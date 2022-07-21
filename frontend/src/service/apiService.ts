import {Game, LoginData, LoginResponse, MyUserCreationData, UserGameDTO} from "./model";
import axios, {AxiosResponse} from "axios";

function requestConfig(){
    return {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`
        }
    }
}

export function createAdminGame(game: Game){
    return axios.post("/api/game/admin", game, requestConfig())
}

export function createUserGame(game: Game){
    return axios.post("/api/game/user", game, requestConfig())
}

export function fetchAllGames(){
    return axios.get("/api/game", requestConfig())
        .then((response: AxiosResponse<Array<Game>>) => response.data)
}

export function getOneGame(gameId: string){
    return axios.get("/api/game/" + gameId, requestConfig())
}

export function switchGameStatus(gameId: string){
    return axios.put("/api/game/" + gameId, gameId, requestConfig())
}

export function postToMyGames(gameId: string){
    return axios.put("/api/game/myGames/" + gameId, gameId, requestConfig())
}

export function removeGameFromMyList(gameId: string){
    return axios.delete("/api/game/myGames/" + gameId, requestConfig())
}

export function fetchAllMyGames(){
    return axios.get("/api/game/myGames", requestConfig())
        .then((response: AxiosResponse<Array<UserGameDTO>>) => response.data)
}

export function updateGameStats(userGameDTO: UserGameDTO){
    return axios.put("/api/game/myGames/update", userGameDTO, requestConfig())
}




//----------- Register and Login -----------//

export function registerUser(myUserCreationData: MyUserCreationData){
    return axios.post(`/api/users`, myUserCreationData)
}

export function sendLogin(loginData: LoginData){
    return axios.post(`/api/login`, loginData)
        .then((response:AxiosResponse<LoginResponse>) => response.data)
}
