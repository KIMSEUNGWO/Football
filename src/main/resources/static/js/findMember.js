const disabled = 'disabled';
let timerInterval = null;
let clickLimit = null;

window.addEventListener('load', () => {

    const phoneBtn = document.querySelector('#phoneBtn');
    phoneBtn.addEventListener('click', () => {
        if (validPhone() != null) return;

        let phone = document.querySelector('input[name="phone"]').value.replaceAll('-', '');
        let box = document.querySelector('#phoneCheckBox');
        box.classList.remove(disabled);
        let json = {phone : phone};

        fetchPost('/sms/send', json, sendSMS);
    })

    const emailBtn = document.querySelector('#phoneCheckBtn.emailBtn');
    if (emailBtn) {
        emailBtn.addEventListener('click', () => {
            let phone = document.querySelector('input[name="phone"]').value;
            let certification = document.querySelector('input[name="phoneCheck"]').value;
    
            let json = {phone : phone.replaceAll('-', ''), certificationNumber : certification};
            fetchPost('/sms/confirm', json, confirmSMS);
        })
    }
    const pwBtn = document.querySelector('#phoneCheckBtn.pwBtn');
    if (pwBtn) {
        pwBtn.addEventListener('click', () => {
            let email = document.querySelector('input[name="email"]').value;
            let phone = document.querySelector('input[name="phone"]').value;
            let certification = document.querySelector('input[name="phoneCheck"]').value;
    
            let json = {email : email, phone : phone.replaceAll('-', ''), certificationNumber : certification};
            fetchPost('/sms/findPassword', json, findPassword);
        })
    }

    const confirmBtn = document.querySelector('#confirm');
    if (confirmBtn) {
        confirmBtn.addEventListener('click', () => {
            let check = confirm('비밀번호를 변경하시겠습니까?');
            if (check) {
                changePassword();
            }
        });
    }



    const phone = document.querySelector('input[name="phone"]');
    phone.addEventListener('keyup',function(e){

        if (e.key === 'Backspace') {
            phone.value = removePhone(phone.value);
        } else {
            var str = removeNotNumber(phone.value);
            phone.value = addPhone(str);
        }
    })

    const email = document.querySelector('input[name="email"]');
    if (email) {
        email.addEventListener('focusout', () => validEmail());
    }

    const close = document.querySelector('#close');
    close.addEventListener('click', () => window.close());


    let passwordCheck = document.querySelector('input[name="passwordCheck"]');
    if (passwordCheck) {
        passwordCheck.addEventListener('keyup', () => validPasswordCheck());
    }
})

function changePassword() {
    let email = document.querySelector('input[name="email"]').value;
    let phone = document.querySelector('input[name="phone"]').value;
    let certification = document.querySelector('input[name="phoneCheck"]').value;

    let pw = document.querySelector('input[name="password"]').value;
    let pwCheck = document.querySelector('input[name="passwordCheck"]').value;

    let json = {email : email, phone : phone.replaceAll('-', ''), certificationNumber : certification, password : pw, passwordCheck : pwCheck};

    fetchPost('/changePassword', json, changePwResult);

}
function changePwResult(result) {
    if (result.result == 'ok') {
        alert('비밀번호를 변경하였습니다.');
        opener.window.reloadPage();
        window.close();
    }
}

function findPassword(result) {
    if (result.result == 'ok') {
        let wrap = document.querySelector('.warp');
        wrap.classList.add(disabled);
        let resultBox = document.querySelector('#resultBox');
        resultBox.classList.remove(disabled);
        let confirmBtn = document.querySelector('#confirm');
        confirmBtn.classList.remove(disabled);
    }
}



function confirmSMS(result) {
    console.log(result)
    if (result.result == 'ok') {
        var cPhone = document.querySelector('.confirmPhoneCheck');
        printTrue(cPhone, result.message);
        let inputPhone = document.querySelector('input[name="phone"]')
        inputPhone.setAttribute('readonly', true);
        clearInterval(timerInterval);
        clearInterval(clickLimit);

        const resultWrap = document.querySelector('.resultWrap');
        let emailResult = document.querySelector('#result');
        if (result.social != null) {
            resultWrap.insertBefore(createSocial(result.social), emailResult);
        }
        if (result.email != null) {
            emailResult.innerHTML = result.email;
        } else {
            emailResult.innerHTML = '가입 이력이 존재하지 않습니다.';
        }

        let resultBox = document.querySelector('.resultBox');
        resultBox.classList.remove(disabled);
    }
}
function createSocial(social) {
    let span = document.createElement('span');
    span.classList.add('social')

    if (social == 'KAKAO') {
        span.classList.add('kakao');
        span.innerHTML = 'KAKAO';
    }
    
    return span;
}
function sendSMS(result) {
    if (result.result == 'ok') {
        if (clickLimit) {
            alert("잠시 후에 시도해주세요.");
            return;
        }
        var cPhone = document.querySelector('.confirmPhone');
        printTrue(cPhone, result.message);
        clearInterval(timerInterval);

        timerInterval = limitTimer();
        clickLimit = limitClick();
    }
}

function validPhone() {
    var cPhone = document.querySelector('.confirmPhone');

    if (checkPhone()){
        printTrue(cPhone, '');
        return null;
    } else {
        printFalse(cPhone, '입력정보가 잘못되었습니다');
        return document.querySelector('input[name="phone"]');
    }
}
function validPassword() {
    let cPassword = document.querySelector('.confirmPassword');
    if (checkPassword()) {
        printTrue(cPassword, '사용가능한 비밀번호 입니다.')
        return null;
    } else {
        printFalse(cPassword, '8자 이상의 대,소문자와 특수문자를 1개 이상 작성해주세요.');
        return document.querySelector("input[name='password']");;
    }
}



function removePhone(str) {
    if (str.endsWith('-')) {
        return str.slice(0, -1);
    }
    return str;
}
function removeNotNumber(text) {
    var newString = text.replace(/[^0-9\-]/, "");
    var distinctString = newString.replace(/-+/g, "-");
    if (distinctString.endsWith('-')) {
        return distinctString.slice(0, -1).replace(/-+/g, "-");
    }
    return distinctString;
}
function addPhone(str) {
    if (str.endsWith('-')) {
        return str;
    }
    if (str.length == 3) {
        return str.slice(0, 3) + '-' + str.slice(3);
    }
    if (str.length == 4) {
        return str.slice(0,3) + '-' + str.slice(3,4);
    }
    if (str.length == 8) {
        return str.slice(0, 8) + '-' + str.slice(8);
    }
    if (str.length == 9) {
        return str.slice(0, 8) + '-' + str.slice(8,9);
    }
    return str;
}

function printTrue(cTag, message) {
    if (cTag != null ){
        cTag.innerHTML = message;
        cTag.classList.remove(disabled)
        cTag.classList.remove("error")
    }
}

function printFalse(cTag, message) {
    if (cTag != null) {
        cTag.innerHTML = message;
        cTag.classList.add("error");
        cTag.classList.remove(disabled);
    }
}

function checkPhone() {
    var validPhobe = document.querySelector('input[name="phone"]');
    var phoneRegex = /^(010)-[0-9]{3,4}-[0-9]{4}$/;
    return phoneRegex.test(validPhobe.value);
}

function limitClick() {
    var seconds = 10;
    var timerId = setInterval(function(){
        // 시간 감소
        seconds--;
        // 시간이 0이면 타이머 중지
        if (seconds === 0) {
            clearInterval(timerId);
            clickLimit = undefined; // 타이머 중지 후 초기화
            return true;
        }
    }, 1000);

    return timerId; // 새로운 타이머 ID 반환
}

function limitTimer() {
    let timer = document.querySelector('.limitTime');
    var minutes = 5;
    var seconds = 0;
    var timerId = setInterval(function(){
        // 시간 감소
        seconds--;
    
        // 시간이 음수가 되면 분 감소
        if (seconds < 0) {
            minutes--;
            seconds = 59;
        }
        if (minutes == 0) {
            timer.style.color = 'red';
        }
    
        // 분과 초를 2자리 숫자로 표시
        var formattedMinutes = ('0' + minutes).slice(-2);
        var formattedSeconds = ('0' + seconds).slice(-2);
    
        // 타이머 업데이트
        timer.textContent = formattedMinutes + ':' + formattedSeconds;
    
        // 시간이 0이면 타이머 중지
        if (minutes === 0 && seconds === 0) {
            clearInterval(timerId);
            alert('타이머 종료!');
            return true;
        }
    }, 1000);

    return timerId; // 새로운 타이머 ID 반환
}

function validEmail(){
    var cEmail = document.querySelector('.confirmEmail');
    if (!checkEmail()){
        printFalse(cEmail, '유효하지 않은 이메일입니다.');
        return document.querySelector("input[name='email']");;
    } else {
        cEmail.classList.add(disabled);
    }
    return null;
}
function validPasswordCheck(){
    var password = document.querySelector('input[name="password"]');
    var passwordCheck = document.querySelector('input[name="passwordCheck"]');

    var cPasswordCheck = document.querySelector('.confirmPasswordCheck');
    if (password.value === passwordCheck.value) {
        printTrue(cPasswordCheck, '비밀번호가 일치합니다.');
        return null;
    } else {
        printFalse(cPasswordCheck, '비밀번호가 일치하지 않습니다.')
        return passwordCheck;
    }
}
function checkEmail() {
    var email = document.querySelector("input[name='email']");
    var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailRegex.test(email.value);
}
function checkPassword() {
    var validPassword = document.querySelector("input[name='password']");
    var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]).{8,15}$/;
    return passwordRegex.test(validPassword.value);
}