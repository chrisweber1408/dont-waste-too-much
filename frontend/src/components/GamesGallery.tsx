import {ApprovedGame, UnapprovedGame} from "../service/model";
import {useNavigate} from "react-router-dom";


interface GamesGalleryProps {
    approvedGames: Array<ApprovedGame>
    unapprovedGames: Array<UnapprovedGame>
}

export default function GamesGallery(props: GamesGalleryProps) {

    const nav = useNavigate()

    return (
        <div>
            <div>
                Approved Games
                {props.approvedGames.map(game => <li>{game.gameName
                    + " money: " + game.spentMoney + "€"}
                    {" time: " + game.playtime + "h"}
                    <button onClick={()=> nav("/approved/infos/" + game.id)}>Edit</button></li>)}
            </div>
            <div>
                Unapproved Games
                {props.unapprovedGames.map(game => <li>{game.gameName
                    + " money: " + game.spentMoney + "€"}
                    {" time: " + game.playtime + "h"}
                    <button onClick={()=> nav("/unapproved/infos/" + game.id)}>Edit</button></li>)}
            </div>
        </div>
    )
}