import {CommunityStatsDTO} from "../../service/model";
import {Doughnut} from "react-chartjs-2"
import {Chart, ArcElement, Legend, Tooltip, Title} from "chart.js"
Chart.register(ArcElement, Legend, Tooltip, Title);

interface gameDoughnutProps{
    game: CommunityStatsDTO
}

export function TotalCommunitySpentMoneyDoughnut(props: gameDoughnutProps){


    const options = {
        responsive: true,
        plugins: {
            legend: {
                display: false,
            },
            title: {
                display: true,
                text: "Total spent money",
                color: "black"
            },
        },
    }

    const doughnutData = {
        labels: [
            'Total game price',
            'Total coins/skins',
            'Total game pass'
        ],
        datasets: [{
            label: 'Total spent money',
            data: [props.game.totalSpentMoneyGame, props.game.totalSpentMoneyCoins, props.game.totalSpentMoneyGamePass],
            borderColor: "black",
            backgroundColor: [
                'rgb(243,121,147)',
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