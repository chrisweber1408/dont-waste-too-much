import {Game} from "../service/model";
import {useState} from "react";
import {useNavigate} from "react-router-dom";


interface GamesGalleryProps {
    games: Array<Game>
}

export default function GamesGallery(props: GamesGalleryProps) {

    const approvedGames = props.games.filter(game => game.approved)
    const unapprovedGames = props.games.filter(game => !game.approved)
    const nav = useNavigate()

    return (
        <div>
            <div>
                Approved Games
                {approvedGames.map(game => <li>{game.gameName
                    + " money: " + game.spentMoney + "€"}
                    {" time: " + game.playtime + "h"}
                    <button onClick={()=> nav("/" + game.id)}>Edit</button></li>)}
            </div>
            <div>
                Unapproved Games
                {unapprovedGames.map(game => <li>{game.gameName
                    + " money: " + game.spentMoney + "€"}
                    {" time: " + game.playtime + "h"}
                    <button onClick={()=> nav("/" + game.id)}>Edit</button></li>)}
            </div>
        </div>

    )
}