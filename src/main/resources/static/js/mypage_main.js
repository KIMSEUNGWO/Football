window.addEventListener('load', () => {
    let x = document.querySelector('#pwdPopCancel');
    let cancelBtn = document.querySelector('#pwdPopCancelBtn');

    let pwdPop = document.querySelector('.passwordPop').parentElement;

    x.addEventListener('click', () => {
        pwdPop.classList.add('disabled');
    })
    cancelBtn.addEventListener('click', () => {
        pwdPop.classList.add('disabled');
    })

    let pwdBtn = document.querySelector('#pwdBtn');
    pwdBtn.addEventListener('click', () => {
        pwdPop.classList.remove('disabled');
    })

    let profileImgBtn = document.querySelector('#img');
    let imgInput = document.querySelector('#img input[type="file"]');

    profileImgBtn.addEventListener('click', () => {
        imgInput.click();
    })

    let profile = document.querySelector('#myProfile');
    imgInput.addEventListener('change', () => {
        let file = imgInput.files[0];
        profile.src = URL.createObjectURL(file);
    })
})