export interface Game {
    id? : string,
    gameName : string,
    approved? : boolean
}

export interface GameData{
    id : string,
    playtime: number,
    money: number
}

export interface LoginResponse{
    jwt: string
}

export interface LoginData {
    username: string
    password: string
}

export interface MyUserCreationData{
    username: string
    password: string
    passwordRepeat: string
}

export interface UserGameDTO{
    username: string
    gameName: string
    gameData: GameData
}