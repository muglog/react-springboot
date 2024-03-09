import { Routes, Route, Link } from "react-router-dom";

import Login from "./components/login/Login";
import GoogleLoginProcess from "./components/login/GoogleLoginProcess";

function App() {
    return (
        <Routes>
            <Route path="/" element={<div>메인페이지</div>} />
            <Route path="/login" element={<Login/>} />
            <Route path="/google/callback" element={<GoogleLoginProcess/>} />
        </Routes>
    );
}

export default App;
