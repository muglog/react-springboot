const sorry = () => {
    alert('아직 구현 못했어요🙏');
}

function Feed(props) {
    return (
        <div style={{backgroundColor: 'white', margin: 0}}>
            <div className="feed_header" style={{ height: '60px', display: 'flex'}}>
                <img className="place_icon" src={`${process.env.PUBLIC_URL}/icon/location_icon.png`}  style={{width: '55px', height: '55px', padding: '0', flex: '1'}} />

                <div style={{padding: '5px 0', flex: '8'}}>
                    <h2 style={{margin: '0'}} >{ props.feed.storeNm }</h2>
                    <small style={{margin: '0'}}>{ props.feed.storeAddr }</small>
                </div>
                <button
                    style={{width: '50px', height: '50px', border: 0, backgroundColor: "transparent", padding: '10px 0', flex: '1'}}
                    onClick={ sorry }
                >
                    <img src={`${process.env.PUBLIC_URL}/icon/hamburger_icon.png`} style={{width: '40px', height: '40px'}} />
                </button>
            </div>

            <div className="feed_image" style={{margin: '0', padding: '0'}}>
                <div style={{width: "100%", aspectRatio: '1'}}>
                    <img src={ props.feed.reviews[0]?.newPhotos[0] } style={{width: "100%", aspectRatio: '1', objectFit: "cover"}}/>
                </div>
            </div>

            <div className="feed_menu" style={{ height: '40px', display: 'flex' }}>
                <div className="left_div" style={{flex: '1', padding: '5px'}}>
                    <h3 style={{margin: '0', fontSize: '18pt'}}>@{props.feed.memNm}</h3>
                </div>
                <div className="right_div" style={{flex: '1'}}>
                    <button
                        style={{width: '40px', height: '40px', fontSize: '30px', border: 0, backgroundColor: "transparent", float:'right'}}
                        onClick={ sorry }
                    >
                        ✉️
                    ️</button>
                    <button
                        style={{width: '40px', height: '40px', fontSize: '30px', border: 0, backgroundColor: "transparent", float:'right'}}
                        onClick={ sorry }
                    >
                        💬
                        ️</button>
                    <button
                        style={{width: '40px', height: '40px', fontSize: '30px', border: 0, backgroundColor: "transparent", float:'right'}}
                        onClick={ sorry }
                    >
                        ❤️
                    ️</button>


                </div>
            </div>

            <div className="feed_content" style={{margin: '5px'}} >
                <h3 className="tags" style={{margin: '10px 0'}}>
                    #{props.feed.reviews[0]?.menuNm} #평점_{props.feed.reviews[0]?.rating}
                </h3>
                <p>
                    { props.feed.reviews[0]?.review }
                </p>
            </div>
        </div>
    );
}

export default Feed;