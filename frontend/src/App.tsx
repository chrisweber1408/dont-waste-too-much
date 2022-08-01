import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import WelcomePage from "./Pages/WelcomePage";
import RegisterPage from "./Pages/RegisterPage";
import MainPage from "./Pages/MainPage";
import InfoPage from "./Pages/InfoPage";
import MyUserGames from "./components/MyUserGames";
import "./App.css"
import CommunityGameInfoPage from "./Pages/CommunityGameInfoPage";


export default function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path={"/"} element={<WelcomePage/>}/>
                <Route path={"/register"} element={<RegisterPage/>}/>
                <Route path={"/main"} element={<MainPage/>}/>
                <Route path={"/infos/:id"} element={<InfoPage/>}/>
                <Route path={"/myGames"} element={<MyUserGames/>}/>
                <Route path={"/communityGameInfo/:id"} element={<CommunityGameInfoPage/>}/>
            </Routes>
        </BrowserRouter>
    )
}
