function Login() {
    const googleLogin = () => {
        window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?
		client_id=${ process.env.REACT_APP_GOOGLE_CLIENT_ID }
		&redirect_uri=${ process.env.REACT_APP_GOOGLE_REDIRECT_URL }
		&response_type=token
		&scope=email profile`;
    };

    return (
        <button onClick={googleLogin}>구글로그인</button>
    );
}

export default Login;
