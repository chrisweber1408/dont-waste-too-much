import {ApprovedGame} from "./model";
import axios, {AxiosResponse} from "axios";

export function createApprovedGame(game: ApprovedGame){
    return axios.post("/api/approved", game)
}

export function createUnapprovedGame(game: ApprovedGame){
    return axios.post("/api/unapproved", game)
}

export function fetchAllGamesApproved(){
    return axios.get("/api/approved")
        .then((response: AxiosResponse<Array<ApprovedGame>>) => response.data)
}
export function fetchAllGamesUnapproved(){
    return axios.get("/api/unapproved")
        .then((response: AxiosResponse<Array<ApprovedGame>>) => response.data)
}

export function getApprovedGame(gameId: string){
    return axios.get("/api/approved/" + gameId)
}

export function getUnapprovedGame(gameId: string){
    return axios.get("/api/unapproved/" + gameId)
}

export function editApprovedGame(game: ApprovedGame){
    return axios.put("/api/approved", game)
}

export function editUnapprovedGame(game: ApprovedGame){
    return axios.put("/api/unapproved", game)
}