window.addEventListener('load', () => {
    
    let kakao = document.querySelector('.login_kakao');

    kakao.addEventListener('click', () => {
        var options = 'width=500, height=600, top=100, left=100, resizable=yes, scrollbars=yes';
        window.open('https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=fa213830a457faaa52a9dbf35a13500e&redirect_uri=http://localhost:8080/login/kakao', '_blank', options);
    })
})
function result(params) {
    console.log(params);
}