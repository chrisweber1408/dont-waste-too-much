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
                {approvedGames.map(game => <li>{game.gameName}</li>)}
            </div>
            <div>
                Unapproved Games
                {unapprovedGames.map(game => <li>{game.gameName}</li>)}
            </div>
        </div>

    )
}