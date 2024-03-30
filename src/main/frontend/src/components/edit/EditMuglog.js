import { Map, MapMarker } from "react-kakao-maps-sdk";
import React, {useState, useEffect} from "react";
import axios from "axios";
import { BottomSheet } from 'react-spring-bottom-sheet'
import 'react-spring-bottom-sheet/dist/style.css'
import {useNavigate} from "react-router-dom";
import useGeoLocation from "./../hooks/useGeolocation.tsx";
const { kakao } = window;

function EditMuglog() {
    const [editStep, setEditStep] = useState(1);
    const [storeInfo, setStoreInfo] = useState(null);

    return (
        <>
            { editStep === 1 ?
                <EditStore editStep={editStep} setEditStep={setEditStep} storeInfo={storeInfo} setStoreInfo={setStoreInfo}/>
                : <EditReview editStep={editStep} setEditStep={setEditStep} storeInfo={storeInfo} setStoreInfo={setStoreInfo}/> }
        </>
    );
}

function EditStore(props) {
    const { kakao } = window;
    const [info, setInfo] = useState()
    const [markers, setMarkers] = useState([])
    const [map, setMap] = useState()
    const [storeSearchResult, setStoreSearchResult] = useState([]);
    const location = useGeoLocation();
    const [center, setCenter] = useState({x: 37.5559, y : 126.9723});

    useEffect(() => {
        if(location.loaded){
            setCenter({x: location.coordinates.lat, y : location.coordinates.lng});
        }
    }, [location]);

    function searchStore(){
        if (!map) return
        const ps = new kakao.maps.services.Places()
        const searchKeyword = document.getElementById("storeSearchKeyword").value;

        if(!searchKeyword){
            alert("검색어를 입력해주세요!");
            return false;
        }

        const options = {
            location: map.getCenter(), size: 5, sort : kakao.maps.services.SortBy.DISTANCE, category_group_code : "FD6,CE7"
        }

        ps.keywordSearch(searchKeyword, (data, status, _pagination) => {
            console.log(status);
            if (status === kakao.maps.services.Status.OK) {
                const bounds = new kakao.maps.LatLngBounds()
                let markers = []
                for (var i = 0; i < data.length; i++) {
                    markers.push({
                        position: {
                            lat: data[i].y,
                            lng: data[i].x,
                        },
                        content: data[i].place_name,
                    })

                    bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x))
                }
                setMarkers(markers)

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
                map.setBounds(bounds)
                setStoreSearchResult(data);
                console.log(data);
            }else if(status === kakao.maps.services.Status.ZERO_RESULT){
                alert("검색 결과가 없습니다");
                let markers = [];
                setMarkers(markers);
                setStoreSearchResult([]);
            }else{
                alert("오류가 발생하였습니다.");
            }
        }, options)
    }

    return (
        <>
            <p>
                음식점 선택
            </p>

            <div className="store-search-wrap">
                <label htmlFor="storeSearchKeyword">🔍 </label>
                <input id="storeSearchKeyword"
                       placeholder="음식점 이름을 입력해주세요!!!"/>
                <button onClick={() => searchStore()}>검색</button>
            </div>

            <div className="store-search-result-wrap" style={{padding: "10px 10px"}}>
                {
                    storeSearchResult.map((storeInfo, index) =>
                        <StoreSearchResultRow storeInfo={storeInfo} setStoreInfo={props.setStoreInfo} key={index}/>
                    )
                }
                <div id="pagination"></div>
            </div>

            <Map // 로드뷰를 표시할 Container
                center={{
                    lat: center.x,
                    lng: center.y,
                }}
                style={{
                    width: "100%",
                    height: "350px",
                }}
                level={3}
                onCreate={setMap}
            >
                {markers.map((marker) => (
                    <MapMarker
                        key={`marker-${marker.content}-${marker.position.lat},${marker.position.lng}`}
                        position={marker.position}
                        onClick={() => setInfo(marker)}
                    >
                        {info &&info.content === marker.content && (
                            <div style={{color:"#000"}}>{marker.content}</div>
                        )}
                    </MapMarker>
                ))}
            </Map>

            {
                props.storeInfo == null ? '' : <>
                    <p dangerouslySetInnerHTML={{__html : props.storeInfo.place_name}}></p>
                    <button onClick={() => {props.setEditStep(2)}}>다음</button>
                </>
            }
        </>
    );

    function StoreSearchResultRow(props){
        function selectStore(storeInfo){
            props.setStoreInfo(storeInfo);

            const bounds = new kakao.maps.LatLngBounds()
            let markers = []

            markers.push({
                position: {
                    lat: storeInfo.y,
                    lng: storeInfo.x,
                },
                content: storeInfo.place_name,
            })

            bounds.extend(new kakao.maps.LatLng(storeInfo.y, storeInfo.x))
            setMarkers(markers)

            map.setBounds(bounds)
        }

        return (
            <div className="store-search-result-row" style={{marginBottom: "5px", display: "flex", border: "1px", borderStyle: "solid", borderRadius: "5px"}}>
                <div className="store-search-result-left-div" style={{padding: "5px", margin: "5px 0px", flexBasis : "85%"}}>
                    <div dangerouslySetInnerHTML={{__html : props.storeInfo.place_name}}></div>
                    <p style={{margin: "0px 0px", fontSize: "12px"}}>{ props.storeInfo.address_name }</p>
                </div>
                <div className="store-search-result-right-div" style={{flexBasis : "15%", textAlign: "center", lineHeight: "50px"}}>
                    <button onClick={() => {selectStore(props.storeInfo)}} style={{margin: "auto", verticalAlign: "middle"}}>선택</button>
                </div>
            </div>
        )
    }
}

function EditReview(props) {
    const navigate = useNavigate();
    const [open, setOpen] = useState(false)
    const [reviews, setReviews] = useState([]);

    const [editMenuReviewIndex, setEditMenuReviewIndex] = useState(null);
    const [menuNmInput, setMenuNmInput] = useState('');
    const [ratingInput, setRatingInput] = useState(5);
    const [reviewInput, setReviewInput] = useState('');
    const [menuImgList, setMenuImgList] = useState([]);

    const resetStore = () => {
        if (window.confirm("음식점을 다시 선택하시겠습니까?")) {
            props.setStoreInfo(null);
            props.setEditStep(1);
        }
    }

    const handleDismiss = () => {
        if (window.confirm(`메뉴 리뷰 ${editMenuReviewIndex == null ? '작성' : '수정'}을 취소하시겠습니까? `)) {
            resetMenuImage();
            setOpen(false);
        } else {
            setOpen(true);
        }
    }

    const openAddMenuModal = () => {
        setMenuNmInput('');
        setRatingInput(5);
        setReviewInput('');
        setMenuImgList([]);
        setEditMenuReviewIndex(null);
        setOpen(true);
    }

    const saveMenuReview = () => {
        let menuInput = document.getElementById("menuNmInput").value;
        let ratingInput = document.getElementById("ratingInput").value;
        let reviewInput = document.getElementById("reviewInput").value;
        let menuImgUrlList = [];

        document.getElementsByName("menuImage").forEach((imgTag) => {
            menuImgUrlList.push(imgTag.src);
        });

        if (!menuInput) {
            alert("메뉴명을 입력해주세요!");
            return;
        } else if (!reviewInput) {
            alert("리뷰를 입력해주세요!");
            return;
        } else if (menuImgUrlList.length == 0){
            alert("사진을 추가해주세요!");
            return;
        }

        const newReviews = [...reviews];
        if(editMenuReviewIndex == null){
            newReviews.push({
                menuNm: menuInput,
                review: reviewInput,
                rating: ratingInput,
                newPhotos: menuImgUrlList
            });
        }else{
            reviews[editMenuReviewIndex].menuNm = menuInput;
            reviews[editMenuReviewIndex].rating = ratingInput;
            reviews[editMenuReviewIndex].review = reviewInput;
            reviews[editMenuReviewIndex].newPhotos = menuImgUrlList;
        }

        setReviews(newReviews);
        resetMenuImage();
        setOpen(false);
    }

    function resetMenuImage(){
        document.getElementsByName("menuImage").forEach((element) => {
            element.remove();
        });
    }

    const inputFileHandle = () => {
        let input = document.getElementById("newPhotos");

        if (input.files) {
            let formData = new FormData();
            let fileCnt = input.files.length;

            for (let i = 0; i < fileCnt; i++) {
                formData.append("files", input.files[i]);
            }

            const jwtToken = localStorage.getItem("muglog_token");
            axios.post('/api/test/upload', formData, {
                headers: {
                    'access_token': jwtToken,
                    "Content-Type": `multipart/form-data; `,
                }
            })
            .then((res) => {
                for (let url of res.data) {
                    const menuImg = document.createElement("img");

                    menuImg.src = url;
                    menuImg.name = 'menuImage';
                    menuImg.style.width = '100px';
                    menuImg.style.height = '100px';
                    menuImg.style.objectFit = 'cover';

                    document.getElementsByClassName("photo-preview-wrap")[0].appendChild(menuImg);
                }
            })
            .catch((error) => {
                alert("파일 업로드 실패");
                document.getElementById("newPhotos").value = '';
            });

            input.value = '';
        }
    }

    function AddMenuSheet() {
        return (
            <BottomSheet open={open}
                         snapPoints={({minHeight, maxHeight}) => [maxHeight * 0.9, maxHeight * 0.9]}
                         onDismiss={handleDismiss}>
                <div style={{display: "block", margin: "10px"}}>
                    <label htmlFor="menuNm">메뉴명 : </label>
                    <input id="menuNmInput" placeholder="메뉴명을 입력해주세요!" defaultValue={menuNmInput}/>
                </div>

                <div style={{display: "block", margin: "10px"}}>
                    <label htmlFor="rating">평점 : </label>
                    <input type="range" id="ratingInput" min="1" max="10" step="1" defaultValue={ratingInput}/>
                </div>

                <div style={{display: "block", margin: "10px"}}>
                    <label htmlFor="rating">사진</label>
                    <input type="file" id="newPhotos" accept="image/*" onChange={inputFileHandle} multiple/>
                    <div className="photo-preview-wrap" style={{display: "grid", justifyContent: "start", gridTemplateColumns : "repeat(auto-fill, minmax(20%, auto)", gap: "10px"}}>
                        {
                            menuImgList.map((url, index) => {
                                return (<img src={url} key={index} name="menuImage" style={{width: "100px", height: "100px", objectFit: "cover"}} />)
                            })
                        }
                    </div>
                </div>

                <div style={{display: "block", margin: "10px"}}>
                    <textarea id="reviewInput" placeholder="리뷰 입력해주세요!" defaultValue={reviewInput}></textarea>
                </div>

                <div style={{display: "block", margin: "10px"}}>
                    <button onClick={handleDismiss}>취소</button>
                    <button onClick={saveMenuReview}>저장</button>
                </div>

            </BottomSheet>
        )
    }

    function openEditReviewModal(index){
        setEditMenuReviewIndex(index);
        setMenuNmInput(reviews[index].menuNm);
        setRatingInput(reviews[index].rating);
        setReviewInput(reviews[index].review);
        setMenuImgList(reviews[index].newPhotos);
        setOpen(true);
    }

    function ReviewRow(props) {
            return (
                <div style={{display: "flex"}} className="review-row">
                    <div className="right-div" style={{display: "flex"}}>
                        <img src={ props.review.newPhotos[0] } style={{width: "80px", height:"80px", objectFit: "cover"}} />
                        <h3>{props.review.menuNm}</h3>
                        <p style={{marginLeft: "10px"}}>{props.review.rating}점</p>
                    </div>
                    <div className="left-div" style={{marginLeft: "auto", marginRight: "20px"}}>
                        <button onClick={ () => openEditReviewModal(props.index) }>수정</button>
                    </div>
                </div>
            )
        }

    const saveReview = (props) => {
            if (reviews.length == 0) {
                alert("리뷰를 입력해주세요ㅠㅠ");
                return;
            }

            const jwtToken = localStorage.getItem("muglog_token");
            if (!jwtToken) {
                alert("로그인 해주세용");
            }

            if (window.confirm("등록하시겠습니까?")) {
                fetch('/api/edit', {
                    method: "POST",
                    headers: {
                        'access_token': jwtToken,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        storeId: props.storeInfo.id,
                        reviews: reviews
                    }),
                })
                    .then((response) => navigate("/"))
                    .then((result) => console.log(result));
            }
        }

    return (
        <>
                <h3>{props.storeInfo.place_name}<span style={{marginLeft: "10px"}}><button onClick={resetStore}>재선택</button></span>
                </h3>
                <hr/>
                <p>메뉴별 리뷰<span style={{marginLeft: "10px"}}><button onClick={openAddMenuModal}>+</button></span></p>
                <div className="menu-review-div" >
                    {
                        reviews.map((review, index) => <ReviewRow key={index} review={review} index={index}/>)
                    }
                </div>

                <AddMenuSheet/>

                <hr/>
                <button onClick={() => saveReview(props)}>등록</button>
        </>
    )
}

export default EditMuglog;
