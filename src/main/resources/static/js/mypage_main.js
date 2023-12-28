window.addEventListener('load', () => {
    let x = document.querySelector('#pwdPopCancel');
    let cancelBtn = document.querySelector('#pwdPopCancelBtn');

    let pwdPop = document.querySelector('.passwordPop').parentElement;
    let deletePop = document.querySelector('.deletePop').parentElement;

    x.addEventListener('click', () => {
        pwdPop.classList.add('disabled');
    })
    cancelBtn.addEventListener('click', () => {
        pwdPop.classList.add('disabled');
    })

    let pwdBtn = document.querySelector('#pwdBtn');
    let deleteBtn = document.querySelector('#deleteBtn');
    pwdBtn.addEventListener('click', () => {
        pwdPop.classList.remove('disabled');
    })
    deleteBtn.addEventListener('click', () => {
        deletePop.classList.remove('disabled');
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


    //

    const deletePopBtn = document.querySelector('#deletePopDeleteBtn');

    let checkBtn = document.querySelector('#check');
    let checkbox = document.querySelector('input[name="policy"]');
    checkBtn.addEventListener('click', () => {
        checkbox.click();
    })

    checkbox.addEventListener('change', () => {
        let boolean = checkbox.checked;
        if (boolean) {
            checkBtn.classList.add('confirm');
            deletePopBtn.setAttribute('data-is-allowed', 'true');
        } else {
            checkBtn.classList.remove('confirm');
            deletePopBtn.setAttribute('data-is-allowed', 'false');
        }
    })

    

    let deleteCancelBtn = document.querySelector('#deletePopCancel');
    let deleteCancelBtn2 = document.querySelector('#deletePopCancelBtn');
    deleteCancelBtn.addEventListener('click', () => {
        deletePop.classList.add('disabled');
    });
    deleteCancelBtn2.addEventListener('click', () => {
        deletePop.classList.add('disabled');
    });

    // password
    let pwSaveBtn = document.querySelector('.saveBtn');
    pwSaveBtn.addEventListener('click', () => {
        let currPw = document.querySelector('input[name="currPassword"]').value;
        let changePw = document.querySelector('input[name="changePassword"]').value;
        let checkPw = document.querySelector('input[name="checkPassword"]').value;

        let json = {nowPassword : currPw, changePassword : changePw, checkPassword : checkPw};
        fetchPost('/change/password', json, result);
    })
})

function result(map) {
    if (map.result == 'fail') {
        if (map.message == null) {
            let currPwError = document.querySelector('#bfpw');
            let changePwError = document.querySelector('#cpw');
            let checkPwError = document.querySelector('#cpwc');
            
            currPwError.innerHTML = (map.nowPwError != null) ? map.nowPwError : '';
            changePwError.innerHTML = (map.changePwError != null) ? map.changePwError : '';
            checkPwError.innerHTML = (map.checkPwError != null) ? map.checkPwError : '';
        } else {
            alert(map.message);
            location.href = '/';
        }
    }
    if (map.result == 'ok') {
        alert(map.message);
        location.href = '/mypage';
    }
}