function fetchPost(url, json, callback) {
    fetch(url , { method : 'post'
					, headers : {'Content-Type' : 'application/json'}
					, body : JSON.stringify(json)
				})
    .then(res => {
        if (!res.ok) {
            // 서버 응답이 OK 상태가 아닌 경우
            return res.json()
                    .then(error => { 
                        throw Error(error.message); // 서버에서 반환한 오류 메시지를 throw하여 catch 블록으로 전달
                    });
        }
        return res.json();
    })
    .then(map => callback(map))
    .catch(error => {
        alert(error.message);
    });
}

function fetchGet(url, callback) {
    fetch(url)
    .then(res => {
        if (!res.ok) {
            // 서버 응답이 OK 상태가 아닌 경우
            return res.json()
                    .then(error => { 
                        throw Error(error.message); // 서버에서 반환한 오류 메시지를 throw하여 catch 블록으로 전달
                    });
        }
        return res.json();
    })
    .then(map => callback(map))
    .catch(error => {
        alert(error.message);
    });
}

