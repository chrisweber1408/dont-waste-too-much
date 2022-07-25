import {Button, Grid, TextField} from "@mui/material";
import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {registerUser} from "../service/apiService";
import Header from "../components/header/Header";


export default function RegisterPage(){

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [passwordRepeat, setPasswordRepeat] = useState("")

    const nav = useNavigate()

    function register(formEvent: FormEvent){
        formEvent.preventDefault()
        registerUser({username, password, passwordRepeat})
            .then(()=> nav("/"))
    }

    return(
        <div>
            <Header/>
            <div>
                <h2 className={"header"}>Nice to meet you!</h2>
            </div>
            <div>
                <form onSubmit={register}>
                    <Grid textAlign={"center"} margin={1}>
                        <TextField  label="Username" variant="outlined" color={"success"} className={"inputfield"} type="text" value={username}
                                    onChange={event => setUsername(event.target.value)}/>
                    </Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <TextField type={"password"} label="Password" color={"success"} variant="outlined" value={password}
                                   onChange={event => setPassword(event.target.value)}/>
                    </Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <TextField type={"password"} label="Password" color={"success"} variant="outlined" value={passwordRepeat}
                                   onChange={event => setPasswordRepeat(event.target.value)}/>
                    </Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <Button variant="contained" type="submit" color={"success"} >Confirm</Button>
                    </Grid>
                    <Grid textAlign={"center"} margin={1}>
                        <Button onClick={()=>nav("/")} variant="contained" color={"success"} >Back</Button>
                    </Grid>
                </form>
            </div>
        </div>
    )
}