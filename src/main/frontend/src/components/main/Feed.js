import React, {useState, useEffect} from "react";

const sorry = () => {
    alert('아직 구현 못했어요🙏');
}

function Feed(props) {
    let [index, setIndex] = useState(0);
    let [reviews, setReviews] = useState([]);
    let [showBeforeBtn, setShowBeforeBtn] = useState(false);
    let [showAfterBtn, setShowAfterBtn] = useState(false);

    useEffect(() => {
        let tmpReviews = []

        const reviewCnt = props.feed.reviews.length;
        for(let i = 0; i < reviewCnt; i++){
            const reviewPhotoCnt = props.feed.reviews[i].newPhotos.length;
            for(let j = 0; j < reviewPhotoCnt; j++){
                tmpReviews.push({
                    menuNm : props.feed.reviews[i]?.menuNm,
                    imgUrl : props.feed.reviews[i]?.newPhotos[j],
                    rating : props.feed.reviews[i]?.rating,
                    review : props.feed.reviews[i]?.review,
                    photoIndex : j + 1,
                    photoCnt : props.feed.reviews[i]?.newPhotos.length
                })
            }
        }

        setReviews(tmpReviews);

        if(tmpReviews.length > 1){
            setShowAfterBtn(true);
        }
    }, []);

    function indexPlus() {
        let i = ++index;
        setIndex(i);

        setShowBeforeBtn(true);
        if (i >= reviews.length - 1) {
            setShowAfterBtn(false);
        }
    }

    function indexMinus(){
        let i = --index;
        setIndex(i);

        setShowAfterBtn(true);
        if (i === 0){
            setShowBeforeBtn(false);
        }
    }

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
                <div style={{width: "100%", aspectRatio: '1', position: 'relative'}}>
                    <div className="btn_wrap" style={{position: 'absolute', width: '100%', height: '100%', display: 'flex'}} >
                        <p style={{ position: 'absolute', top: '10px', left: '10px', margin: '0', padding: '3px 5px', backgroundColor: 'rgba(0, 0, 0, .3)', borderRadius: '4px', color: 'white' }}>
                            {reviews[index]?.photoIndex} / {reviews[index]?.photoCnt}
                        </p>

                        {
                        showBeforeBtn ?
                            <button
                                style={{ position: 'absolute', top: '50%', left: '3%', transform: 'translate(0, -50%)', fontSize: '40px', lineHeight: '40px', textAlign: 'center', width: '60px', height: '60px', borderRadius: '60px', border: "none", backgroundColor: 'rgba(0, 0, 0, .3)'}}
                                onClick={indexMinus}
                            >👈</button>
                            : null
                        }

                        {
                        showAfterBtn ?
                            <button
                                style={{ position: 'absolute', top: '50%', right: '3%', transform: 'translate(0, -50%)', fontSize: '40px', lineHeight: '40px', textAlign: 'center', width: '60px', height: '60px', borderRadius: '60px', border: "none", backgroundColor: 'rgba(0, 0, 0, .3)'}}
                                onClick={indexPlus}
                            >👉</button>
                            : null
                        }
                    </div>
                    <img src={ reviews[index]?.imgUrl } style={{width: "100%", aspectRatio: '1', objectFit: "cover"}}/>
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
                    #{ reviews[index]?.menuNm} #평점_{reviews[index]?.rating}
                </h3>
                <p>
                    { reviews[index]?.review }
                </p>
            </div>
            <hr style={{ height: '10px', border: '0', boxShadow: '0 10px 10px -10px #8c8c8c inset' }}/>
        </div>
    );
}

export default Feed;