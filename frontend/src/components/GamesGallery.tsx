import {game} from "../service/model";
import {useNavigate} from "react-router-dom";


interface GamesGalleryProps {
    games: Array<game>
}

export default function GamesGallery(props: GamesGalleryProps) {

    const approvedGames = props.games.filter(game => game.approved)
    const unapprovedGames = props.games.filter(game => !game.approved)

    const nav = useNavigate()

    return (
        <div>
            <div>
                Approved Games
                {approvedGames.map(game => <li>{game.gameName}
                    <button onClick={()=> nav("/infos/" + game.id)}>Edit</button></li>)}
            </div>
            <div>
                Unapproved Games
                {unapprovedGames.map(game => <li>{game.gameName}
                    <button onClick={()=> nav("/infos/" + game.id)}>Edit</button></li>)}
            </div>
        </div>
    )
}