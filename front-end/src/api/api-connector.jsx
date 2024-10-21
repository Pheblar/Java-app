import Axios from "axios";

const apiUrl = process.env.REACT_APP_API_URL;
const apiPort = process.env.REACT_APP_API_PORT;

export async function getPostData(){
    return await Axios.get(`${apiUrl}:${apiPort}/api/v1/posts`)
        .then(res => {
            console.log("getPostData"+res.data);
            return res.data
        })
        .catch(err => console.log(err));
}

export async function savePost(postData){
    return await Axios.post(`${apiUrl}:${apiPort}/api/v1/create-post`, postData,
        {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(res => res.data)
        .catch(err => console.log(err));
}
