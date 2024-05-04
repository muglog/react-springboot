import Feed from "./Feed";
import React, {useState, useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function Main() {
    const navigate = useNavigate();
    const [feedList, setFeedList] = useState([]);

    const moveToEditPage = () => {
        const jwtToken = localStorage.getItem("muglog_token");
        if (!jwtToken) {
            alert("로그인 해주세용");
            navigate('/login');
            return;
        }

        fetch('/api/login/isLogined', {
            headers: {
                'access_token': jwtToken
            }
        })
        .then((response) => {
            console.log(response);
            navigate("/edit");
        })
        .catch((error) => {
            alert('로그인해주세용');
            navigate('/login');
        });
    }

    useEffect(() => {
        axios.get('/api/main')
        .then((res) => {
            console.log(res.data);
            setFeedList(res.data);
        })
        .catch((error) => {
            alert("조회 실패 ㅠㅠ");
        });
    }, [])

    return (
        <>
            {
                feedList.map((feed, index) => <Feed key={index} feed={feed}/>)
            }
            <button style={{ position:'fixed', width: '60px', height: '60px', bottom: '40px', right: '40px',
                backgroundColor: 'red', color: 'white', borderRadius: '50px',
                fontSize: '30pt', border: 0,
                textAlign: 'center', boxShadow: '2px 2px 3px #999'}}
                onClick={ moveToEditPage }
            >
                +
            </button>
        </>
    );
}

export default Main;