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

export interface NewStatsDTO{

    gameId: string
    addedPlaytime: number
    addedSpentMoneyGamePass: number
    addedSpentMoneyCoins: number
    addedSpentMoneyGame: number

}

export interface CommunityStatsDTO{

    gameName: string
    totalPlaytime: number
    totalSpentMoneyGame: number
    totalSpentMoneyCoins: number
    totalSpentMoneyGamePass: number
    averagePlaytime: number
    averageSpentMoneyGame: number
    averageSpentMoneyCoins: number
    averageSpentMoneyGamePass: number

}