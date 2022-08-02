import {CommunityStatsDTO} from "../../service/model";
import {Doughnut} from "react-chartjs-2"
import {Chart, ArcElement, Legend, Tooltip, Title} from "chart.js"
Chart.register(ArcElement, Legend, Tooltip, Title);

interface gameDoughnutProps{
    game: CommunityStatsDTO
}

export function TotalCommunityMoneyVsPlaytimeDoughnut(props: gameDoughnutProps){


    const options = {
        responsive: true,
        plugins: {
            legend: {
                display: false,
            },
            title: {
                display: true,
                text: "Total money vs time",
                color: "black"
            },
        },
    }

    const doughnutData = {
        labels: [
            'Total spent money',
            'Total playtime'
        ],
        datasets: [{
            label: 'Spent money',
            data: [props.game.totalSpentMoneyGame + props.game.totalSpentMoneyCoins + props.game.totalSpentMoneyGamePass, props.game.totalPlaytime],
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