import "./Header.css"
import LogoutIcon from '@mui/icons-material/Logout';
import HomeIcon from '@mui/icons-material/Home';
import SportsEsportsIcon from '@mui/icons-material/SportsEsports';
import {useNavigate} from "react-router-dom";
import {Grid} from "@mui/material";
import {ToastContainer} from "react-toastify";

export default function Header(){

    function logout(){
        localStorage.setItem("jwt", "")
    }


    const nav = useNavigate()

    return(
        <div>
            <ToastContainer/>
            <div>
                <h1 className={"header"}>Dont waste too much!</h1>
            </div>
            <Grid container>
                <Grid item xs={0.5}>
                </Grid>
                <Grid onClick={()=> nav("/main")} item xs={3} textAlign={"center"} border={2} borderRadius={4}>
                    <HomeIcon fontSize={"large"}></HomeIcon>
                </Grid>
                <Grid item xs={1}>
                </Grid>
                <Grid onClick={()=> nav("/myGames")} item xs={3} textAlign={"center"} border={2} borderRadius={4}>
                    <SportsEsportsIcon fontSize={"large"}></SportsEsportsIcon>
                </Grid>
                <Grid item xs={1}>
                </Grid>
                <Grid onClick={()=> nav("/")} onClickCapture={logout} item xs={3} textAlign={"center"} border={2} borderRadius={4}>
                    <LogoutIcon fontSize={"large"}></LogoutIcon>
                </Grid>
                <Grid item xs={0.5}>
                </Grid>
            </Grid>
        </div>
    )
}