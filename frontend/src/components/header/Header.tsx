import "./Header.css"
import LogoutIcon from '@mui/icons-material/Logout';
import HomeIcon from '@mui/icons-material/Home';
import SportsEsportsIcon from '@mui/icons-material/SportsEsports';
import {useNavigate} from "react-router-dom";
import {Grid} from "@mui/material";

export default function Header(){

    function logout(){
        localStorage.setItem("jwt", "")
    }


    const nav = useNavigate()

    return(
        <div>
            <div>
                <h1 className={"header"}>Dont waste too much!</h1>
            </div>
            <Grid container>
                <Grid onClick={()=> nav("/main")} item xs={3} textAlign={"center"} border={2} borderRadius={4} margin={2}>
                    <HomeIcon fontSize={"large"}></HomeIcon>
                </Grid>
                <Grid onClick={()=> nav("/myGames")} item xs={3} textAlign={"center"} border={2} borderRadius={4} margin={2}>
                    <SportsEsportsIcon fontSize={"large"}></SportsEsportsIcon>
                </Grid>
                <Grid onClick={()=> nav("/")} onClickCapture={logout} item xs={3} textAlign={"center"} border={2} borderRadius={4} margin={2}>
                    <LogoutIcon fontSize={"large"}></LogoutIcon>
                </Grid>
            </Grid>
        </div>
    )
}