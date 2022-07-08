import {Game} from "./model";
import axios from "axios";

export function createGame(game: Game){
    return axios.post("/api/game", game)
}