import { useNavigate } from 'react-router-dom';
import React, { useEffect } from 'react';
import axios from "axios";

const GoogleLoginProcess = () => {
    const navigate = useNavigate();

    // 현재 url에서 access_token 추출
    const hash = window.location.hash;
    const accessToken = hash.substring(hash.indexOf('=') + 1, hash.indexOf('&'));

    const handleLoginPost = accessToken => {
        const data = {
            accessToken: accessToken,
        };

        try {
            const res = axios.post(
                "/api/login/googleLogin",
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
        if (accessToken) {
            handleLoginPost(accessToken);
        } else {
            console.log("로그인 재시도하세요.");
        }
    }, [accessToken, navigate]);
    return (
        <p>~ 로그인중 ~</p>
    );
}

export default GoogleLoginProcess;