window.addEventListener('load', () => {
    
    let kakao = document.querySelector('.login_kakao');

    kakao.addEventListener('click', () => {
        var options = 'width=500, height=600, top=100, left=100, resizable=yes, scrollbars=yes';
        window.open('https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=fa213830a457faaa52a9dbf35a13500e&redirect_uri=http://localhost:8080/login/kakao', '_blank', options);
    })

    const findEmail = document.querySelector('#findEmail');
    const findPassword = document.querySelector('#findPassword');

    // findEmail.addEventListener('click', () => popup('/findEmail'));
    findEmail.addEventListener('click', () => popup('http://127.0.0.1:3000/templates/login/findEmail.html'));
    
    // findPassword.addEventListener('click', () => popup('/findPassword'));
})

function popup(url) {
    var options = 'width=400, height=500, top=100, left=100, resizable=yes, scrollbars=yes';
    window.open(url, '_blank', options);
}