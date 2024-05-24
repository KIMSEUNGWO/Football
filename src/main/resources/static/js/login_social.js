window.addEventListener('load', () => {
    
    let kakao = document.querySelector('.login_kakao');

    kakao.addEventListener('click', () => {
        var options = 'width=500, height=600, top=100, left=100, resizable=yes, scrollbars=yes';
        window.open('/oauth2/authorization/kakao', '_blank', options);
    })

    const findEmail = document.querySelector('#findEmail');
    const findPassword = document.querySelector('#findPassword');

    findEmail.addEventListener('click', () => popup('/findEmail', 500));
    findPassword.addEventListener('click', () => popup('/findPassword', 450));
    // findEmail.addEventListener('click', () => popup('http://127.0.0.1:3000/templates/login/findEmail.html', 500));
    // findPassword.addEventListener('click', () => popup('http://127.0.0.1:3000/templates/login/findPassword.html', 450),);

})

function popup(url, height) {
    var options = 'width=400, height='+ height + ', top=100, left=100, resizable=no, scrollbars=yes';
    window.open(url, '_blank', options);
}