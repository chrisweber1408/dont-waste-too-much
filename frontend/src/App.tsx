import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import WelcomePage from "./Pages/WelcomePage";
import RegisterPage from "./Pages/RegisterPage";
import MainPage from "./Pages/MainPage";
import ApprovedInfoPage from "./Pages/ApprovedInfoPage";
import UnapprovedInfoPage from "./Pages/UnapprovedInfoPage";

export default function App() {

    return(
        <BrowserRouter>
            <Routes>
                <Route path={"/login"} element={<WelcomePage/>}/>
                <Route path={"/register"} element={<RegisterPage/>}/>
                <Route path={"/"} element={<MainPage/>}/>
                <Route path={"/approved/infos/:id"} element={<ApprovedInfoPage/>}/>
                <Route path={"/unapproved/infos/:id"} element={<UnapprovedInfoPage/>}/>
            </Routes>
        </BrowserRouter>
    )
}
