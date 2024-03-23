import { useNavigate } from 'react-router-dom';
import React, { useEffect } from 'react';
import axios from "axios";

const NaverLoginProcess = () => {
    const navigate = useNavigate();

    debugger
    const searchParams = new URL(document.location).searchParams;
    const code = searchParams.get('code');
    const state = searchParams.get('state');

    const handleLoginPost = () => {
        const data = {
            code: code,
            state: state
        };

        try {
            const res = axios.post(
                `/api/login/naverLogin`,
                data,
            ).then(function (res){
                const jwt = res.data.jwt;
                const userName = res.data.name;
                const userEmail = res.data.email;

                // 로컬 스토리지에 jwt 토큰 저장
                localStorage.setItem("muglog_token", jwt);

                const redirectUrl = `/login/success?name=${userName}&email=${userEmail}`;
                navigate(redirectUrl);
                window.location.reload();
            })
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        if (code, state) {
            handleLoginPost(code, state);
        } else {
            console.log("로그인 재시도하세요.");
        }
    }, [code, state, navigate]);
    return (
        <p>~ 로그인중 ~</p>
    );
}

export default NaverLoginProcess;