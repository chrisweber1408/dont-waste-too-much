export interface game {
    id? : string,
    gameName : string,
    approved? : boolean
}

export interface LoginResponse{
    token: string
}