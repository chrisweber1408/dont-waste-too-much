import {Game} from "../service/model";
import {useNavigate} from "react-router-dom";
import {postToMyGames} from "../service/apiService";
import {useState} from "react";


interface GamesGalleryProps {
    games: Array<Game>
}

export default function GamesGallery(props: GamesGalleryProps) {

    const approvedGames = props.games.filter(game => game.approved)
    const unapprovedGames = props.games.filter(game => !game.approved)
    const [errorMessageFav, setErrorMessageFav] = useState("")

    const nav = useNavigate()

    function addToMyGames(id: string | undefined){
        if(id){
            postToMyGames(id)
                .catch(()=> setErrorMessageFav("Game already added to your list"))
        }
    }

    return (
        <div>
            <div>
                {errorMessageFav}
            </div>
            <div>
                Approved Games
                {approvedGames.map(game => <li>{game.gameName}
                    <button onClick={()=> addToMyGames(game.id)}>Add</button>
                    <button onClick={()=> nav("/infos/" + game.id)}>Info</button></li>)}
            </div>
            <div>
                Unapproved Games
                {unapprovedGames.map(game => <li>{game.gameName}
                    <button onClick={()=> addToMyGames(game.id)}>Add</button>
                    <button onClick={()=> nav("/infos/" + game.id)}>Info</button></li>)}
            </div>
        </div>
    )
}