import { Routes, Route, Link } from "react-router-dom";

import Login from "./components/login/Login";
import GoogleLoginProcess from "./components/login/GoogleLoginProcess";
import LoginSuccess from "./components/login/LoginSuccess";
import NaverLoginProcess from "./components/login/NaverLoginProcess";

function App() {
    return (
        <Routes>
            <Route path="/" element={<div>메인페이지</div>} />
            <Route path="/login" element={<Login/>} />
            <Route path="/google/callback" element={<GoogleLoginProcess/>} />
            <Route path="/naver/callback" element={<NaverLoginProcess/>} />
            <Route path="/login/success" element={<LoginSuccess/>} />
        </Routes>
    );
}

export default App;
