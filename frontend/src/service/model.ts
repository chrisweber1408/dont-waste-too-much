export interface Game {
    id? : string,
    gameName : string,
    approved? : boolean
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
    playtime: number
    spentMoneyGamePass: number
    spentMoneyCoins: number
    spentMoneyGame: number
    gameId: string
    approved: boolean
}