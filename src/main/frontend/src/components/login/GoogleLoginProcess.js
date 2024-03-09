import { useNavigate } from 'react-router-dom';
import React, { useEffect } from 'react';
import axios from "axios";

const GoogleLoginProcess = () => {
    const navigate = useNavigate();

    // 이미 가입한 유저일 시 : 메인 페이지로 이동
    const handleHome = () => {
        navigate("/");
        window.location.reload();
    };

    // 현재 url에서 code 부분 추출
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
                const jwt = res.data;
                console.log(res);
                localStorage.setItem("muglog_token", jwt);

                handleHome();
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