import {useEffect, useState} from "react";
import axios from "axios";

let index = 0;

function App() {
  const [char, setChar] = useState('');

  useEffect(() => {
    axios.get('/api/test/getChar')
        .then((res) => {
          setChar(res.data);
        })
  }, []);
  return (
      <div className="App">
        <button onClick={ ()=> {
            axios.get('/api/test/getChar', {params: {index: index}})
                .then((res) => {
                    setChar(res.data);
                    index = index > 5 ?  0 : index+1;
                })
                .catch(() => console.log('요청 실패'))}
        }>인사</button>
        <p>{ char }</p>
      </div>
  );
}

export default App;
