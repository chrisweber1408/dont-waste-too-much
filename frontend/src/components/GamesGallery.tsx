import {Game} from "../service/model";


interface GamesGalleryProps{
    games: Array<Game>
}

export default function GamesGallery(props: GamesGalleryProps){

    const approvedGames = props.games.filter(game => game.approved)
    const unapprovedGames = props.games.filter(game => !game.approved)

    return(
        <div>
            <div>
                Approved Games
                {approvedGames.map(game => <div>{game.gameName
                    + " money: " + game.spentMoney
                    + " time: " + game.playtime} <input size={2}/></div>)}
            </div>
            <div>
                Unapproved Games
                {unapprovedGames.map(game => <li>{game.gameName
                    + " money: " + game.spentMoney
                    + " time: " + game.playtime}</li>)}
        </div>
        </div>

    )
}