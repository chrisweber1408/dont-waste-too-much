import {Button, Grid, TextField} from "@mui/material";
import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {sendLogin} from "../service/apiService";
import Header from "../components/header/Header";
import LoginIcon from '@mui/icons-material/Login';


export default function RegisterPage() {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")


    const nav = useNavigate()

    function login(formEvent: FormEvent) {
        formEvent.preventDefault()
        sendLogin({username, password})
            .then(data => localStorage.setItem("jwt", data.jwt))
            .then(() => nav("/main"))
    }

    return (
        <div>
            <Header/>
            <div>
                <form onSubmit={login}>
                    <Grid textAlign={"center"} margin={1}>
                        <TextField label="Username" variant="outlined" color={"success"} type="text" value={username}
                                   onChange={event => setUsername(event.target.value)}/>
                    </Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <TextField type={"password"} label="Password" color={"success"} variant="outlined" value={password}
                                   onChange={event => setPassword(event.target.value)}/>
                    </Grid>
                    <Grid item xs={6} textAlign={"center"}>
                        <Button onClick={() => nav("/register")} variant="contained" color={"success"}>Register</Button>
                    </Grid>
                    <Grid item xs={6} textAlign={"center"} margin={1}>
                        <Button variant="contained" type="submit" color={"success"}>Login</Button>
                    </Grid>
                </form>
                <div className="g-signin2" data-onsuccess="onSignIn"></div>
            </div>
        </div>
    )
}