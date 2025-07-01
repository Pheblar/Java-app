import Axios from "axios";

export async function getPostData(){
    return await Axios.get(`/api/v1/posts`)
        .then(res => {
            console.log("getPostData"+res.data);
            return res.data
        })
        .catch(err => console.log(err));
}

export async function savePost(postData){
    return await Axios.post(`/api/v1/create-post`, postData,
        {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(res => res.data)
        .catch(err => console.log(err));
}
