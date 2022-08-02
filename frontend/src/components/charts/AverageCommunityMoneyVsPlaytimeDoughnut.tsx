import {CommunityStatsDTO} from "../../service/model";
import {Bar} from "react-chartjs-2"
import {Chart, ArcElement, LineElement, BarElement, PointElement, BarController, BubbleController, DoughnutController, LineController, PieController, PolarAreaController,
    RadarController, ScatterController, CategoryScale, LinearScale, LogarithmicScale, RadialLinearScale, TimeScale, TimeSeriesScale, Decimation, Filler, Legend, Title,
    Tooltip} from 'chart.js';
Chart.register(ArcElement, LineElement, BarElement, PointElement, BarController, BubbleController, DoughnutController, LineController, PieController, PolarAreaController,
    RadarController, ScatterController, CategoryScale, LinearScale, LogarithmicScale, RadialLinearScale, TimeScale, TimeSeriesScale, Decimation, Filler, Legend, Title,
    Tooltip);

interface gameDoughnutProps{
    game: CommunityStatsDTO
}

export function AverageCommunityMoneyVsPlaytimeDoughnut(props: gameDoughnutProps){

    const options = {
        responsive: true,
        plugins: {
            legend: {
                display: false,
            },
            title: {
                display: true,
                text: "Average money vs time",
                color: "black"
            },
        },
    }

    const doughnutData = {
        labels: [
            'Average spent money',
            'Average playtime'
        ],
        datasets: [{
            label: 'Spent money',
            data: [props.game.averageSpentMoneyGame + props.game.averageSpentMoneyCoins + props.game.averageSpentMoneyGamePass, props.game.averagePlaytime],
            borderColor: "black",
            backgroundColor: [
                'rgb(97,172,227)',
                'rgb(234,198,115)'
            ],
            hoverOffset: -10
        }],
    };

    return(
        <Bar options={options} data={doughnutData}/>
    )
}