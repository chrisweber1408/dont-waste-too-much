export interface game {
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