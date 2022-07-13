import {Game} from "./model";
import axios, {AxiosResponse} from "axios";

export function createGame(game: Game){
    return axios.post("/api/game", game)
}

export function fetchAllGames(){
    return axios.get("/api/game")
        .then((response: AxiosResponse<Array<Game>>) => response.data)
}

export function getGame(gameId: string){
    return axios.get("/api/game/" + gameId)
}

export function editGame(game: Game){
    return axios.put("/api/game", game)
}