import GamesGallery from "../components/GamesGallery";
import {
    createApprovedGame,
    createUnapprovedGame,
    fetchAllGamesApproved,
    fetchAllGamesUnapproved
} from "../service/apiService";
import {useEffect, useState} from "react";
import {ApprovedGame, UnapprovedGame} from "../service/model";

export default function MainPage(){


    const [userGame, setUserGame] = useState(localStorage.getItem("userGame") ?? "")
    const [adminGame, setAdminGame] = useState(localStorage.getItem("adminGame") ?? "")
    const [approvedGames, setApprovedGames] = useState<Array<ApprovedGame>>([])
    const [unapprovedGames, setUnapprovedGames] = useState<Array<UnapprovedGame>>([])
    const [errorMessageLoadApprovedGames, setErrorMessageLoadApprovedGames] = useState("")
    const [errorMessageLoadUnapprovedGames, setErrorMessageLoadUnapprovedGames] = useState("")
    const [errorMessageCreateApprovedGames, setErrorMessageCreateApprovedGames] = useState("")
    const [errorMessageCreateUnapprovedGames, setErrorMessageCreateUnapprovedGames] = useState("")


    useEffect(()=>{
        localStorage.setItem("userGame", userGame)
    },[userGame])

    useEffect(()=>{
        localStorage.setItem("adminGame", adminGame)
    },[adminGame])

    useEffect(()=>{
        fetchAllApprovedGames()
        fetchAllUnapprovedGames()
    },[userGame, adminGame])

    const fetchAllApprovedGames = ()=>{
        fetchAllGamesApproved()
            .then((approvedGamesFromDb: Array<ApprovedGame>) => setApprovedGames(approvedGamesFromDb))
            .then(()=> setErrorMessageLoadApprovedGames(""))
            .catch(()=> setErrorMessageLoadApprovedGames("The approved games could not be loaded"))
    }
    const fetchAllUnapprovedGames = ()=>{
        fetchAllGamesUnapproved()
            .then((unapprovedGamesFromDb: Array<UnapprovedGame>) => setUnapprovedGames(unapprovedGamesFromDb))
            .then(()=> setErrorMessageLoadUnapprovedGames(""))
            .catch(()=> setErrorMessageLoadUnapprovedGames("The unapproved games could not be loaded"))
    }
    function saveApprovedGame(){
        createApprovedGame({gameName: adminGame, playtime: 0, spentMoney: 0, approved: true})
            .then(()=> setAdminGame(""))
            .then(()=> setErrorMessageCreateApprovedGames(""))
            .catch(()=> setErrorMessageCreateApprovedGames("The Game could not be created"))
    }
    function saveUnapprovedGame(){
        createUnapprovedGame({gameName: userGame, playtime: 0, spentMoney: 0, approved: false})
            .then(()=> setUserGame(""))
            .then(()=> setErrorMessageCreateUnapprovedGames(""))
            .catch(()=> setErrorMessageCreateUnapprovedGames("The Game could not be created"))
    }




    return(
        <div>
            <span>{errorMessageLoadApprovedGames}</span>
            <span>{errorMessageLoadUnapprovedGames}</span>
            <span>{errorMessageCreateApprovedGames}</span>
            <span>{errorMessageCreateUnapprovedGames}</span>
            <div>
                User
                <input type={"text"} placeholder={"Game to add"} value={userGame} onChange={event => setUserGame(event.target.value)}/>
                <button onClick={saveUnapprovedGame}>Add Game</button>
            </div>
            <div>
                Admin
                <input type={"text"} placeholder={"Game to add"} value={adminGame} onChange={event => setAdminGame(event.target.value)}/>
                <button onClick={saveApprovedGame}>Add Game</button>
            </div>
            <div>
                <GamesGallery approvedGames={approvedGames} unapprovedGames={unapprovedGames}/>
            </div>
        </div>

    )

}