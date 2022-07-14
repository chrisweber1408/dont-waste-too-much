import {game} from "./model";
import axios, {AxiosResponse} from "axios";

export function createAdminGame(game: game){
    return axios.post("/api/game/admin", game)
}

export function createUserGame(game: game){
    return axios.post("/api/game/user", game)
}

export function fetchAllGames(){
    return axios.get("/api/game")
        .then((response: AxiosResponse<Array<game>>) => response.data)
}

export function getApprovedGame(gameId: string){
    return axios.get("/api/game/" + gameId)
}

export function getUnapprovedGame(gameId: string){
    return axios.get("/api/unapproved/" + gameId)
}

export function editApprovedGame(game: game){
    return axios.put("/api/approved", game)
}

export function editUnapprovedGame(game: game){
    return axios.put("/api/unapproved", game)
}