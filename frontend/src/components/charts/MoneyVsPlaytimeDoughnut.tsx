import {UserGameDTO} from "../../service/model";
import {Doughnut} from "react-chartjs-2"
import {Chart, ArcElement, Legend, Tooltip, Title} from "chart.js"
Chart.register(ArcElement, Legend, Tooltip, Title);

interface gameDoughnutProps{
    Game: UserGameDTO
}

export function MoneyVsPlaytimeDoughnut(props: gameDoughnutProps){


    const options = {
        responsive: true,
        plugins: {
            legend: {
                display: false,
            },
            title: {
                display: true,
                text: "Money vs time",
                color: "black"
            },
        },
    }

    const doughnutData = {
        labels: [
            'Spent money',
            'Playtime'
        ],
        datasets: [{
            label: 'Spent money',
            data: [props.Game.spentMoneyGame + props.Game.spentMoneyCoins + props.Game.spentMoneyGamePass, props.Game.playtime],
            borderColor: "black",
            backgroundColor: [
                'rgb(97,172,227)',
                'rgb(234,198,115)'
            ],
            hoverOffset: -10
        }],
    };

    return(
        <Doughnut options={options} data={doughnutData}/>
    )
}