import {Button, Grid, TextField} from "@mui/material";
import {FormEvent, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {sendLogin} from "../service/apiService";
import Header from "../components/header/Header";
import {toast} from "react-toastify";

export default function RegisterPage() {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const nav = useNavigate()

    useEffect(()=>{
        if (localStorage.getItem("jwt") !== null && localStorage.getItem("jwt") !== ""){
            nav("/main")
        }
    },[])


    function login(formEvent: FormEvent) {
        formEvent.preventDefault()
        sendLogin({username, password})
            .then(data => localStorage.setItem("jwt", data.jwt))
            .then(() => nav("/main"))
            .catch(()=> toast.warning("Wrong username/password!"))
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
                    <Grid item xs={6} textAlign={"center"} margin={1}>
                        <Button variant="contained" type="submit" color={"success"}>Login</Button>
                    </Grid>
                    <Grid textAlign={"center"} fontSize={30}>No account?</Grid>
                    <Grid item xs={6} textAlign={"center"} margin={1}>
                        <Button onClick={() => nav("/register")} variant="contained" color={"success"}>Sign in now!</Button>
                    </Grid>
                </form>
                <div className="g-signin2" data-onsuccess="onSignIn"></div>
            </div>
        </div>
    )
}