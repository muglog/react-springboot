const LoginSuccess = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const name = urlParams.get("name");
    const email = urlParams.get("email");

    return (
        <p>
            {
                name ? name + "("+email+")님 안녕하세요!" : "유저 정보 조회실패"
            }
        </p>
    );
}

export default LoginSuccess;