import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import WelcomePage from "./Pages/WelcomePage";
import RegisterPage from "./Pages/RegisterPage";
import MainPage from "./Pages/MainPage";

export default function App() {

    return(
        <BrowserRouter>
            <Routes>
                <Route path={"/login"} element={<WelcomePage/>}/>
                <Route path={"/register"} element={<RegisterPage/>}/>
                <Route path={"/"} element={<MainPage/>}/>
            </Routes>
        </BrowserRouter>
    )


}
