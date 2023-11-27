const disabled = 'disabled';

window.addEventListener('load', function(){

    let form = document.querySelector('.register_form');
    form.addEventListener('submit', function(e){
    })

    let distinctEmail = document.querySelector('.distinctEmailBtn');
    distinctEmail.addEventListener('click', function(){
        validEmail()
    })

    let passwordCheck = document.querySelector('input[name="passwordCheck"]');
    passwordCheck.addEventListener('keyup', function(e){
        if (passwordCheck.isEqualNode(e.target)){
            validPasswordCheck();
        }
    })

    let phoneBtn = document.querySelector('#phoneBtn');
    phoneBtn.addEventListener('click', function(e){
        if (phoneBtn.isEqualNode(e.target)){
            validPhone();
        }
    })

    let phone = document.querySelector('input[name="phone"]');
    phone.addEventListener('keyup',function(e){

        if (phone.isEqualNode(e.target)) {
            if (e.key === 'Backspace') {
                phone.value = removePhone(phone.value);
            } else {
                var str = removeNotNumber(phone.value);
                phone.value = addPhone(str);
            }
        }
    })

    // let birthday = document.querySelector('input[name="birthday"]');
    // birthday.addEventListener('keyup', function(e){
    //     if (birthday.isEqualNode(e.target)) {
    //         if (e.key === 'Backspace') {
    //             birthday.value = removePhone(birthday.value);
    //         } else {
    //             var str = removeNotNumber(birthday.value);
    //             birthday.value = addBirthday(str);
    //         }
    //     }
    // })


})

function removePhone(str) {
    if (str.endsWith('-')) {
        return str.slice(0, -1);
    }
    return str;
}

function addBirthday(str) {
    if (str.endsWith('-')) {
        return str;
    }
    if (str.length == 4) {
        return str.slice(0, 4) + '-' + str.slice(4);
    }
    if (str.length == 5) {
        return str.slice(0, 4) + '-' + str.slice(4,5);
    }
    if (str.length == 7) {
        return str.slice(0, 7) + '-' + str.slice(7);
    }
    if (str.length == 8) {
        return str.slice(0, 7) + '-' + str.slice(7,8);
    }
    return str;
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

function removeNotNumber(text) {
    var newString = text.replace(/[^0-9\-]/, "");
    var distinctString = newString.replace(/-+/g, "-");
    if (distinctString.endsWith('-')) {
        return distinctString.slice(0, -1).replace(/-+/g, "-");
    }
    return distinctString;
}

function printTrue(eTag, cTag, cMsg) {
    if (eTag != null) {
        eTag.classList.add(disabled);
    }
    if (cTag != null ){
        cTag.innerHTML = cMsg;
        cTag.classList.remove(disabled)
    }
}

function printFalse(eTag, cTag, eMsg) {
    if (eTag != null) {
        eTag.innerHTML = eMsg;
        eTag.classList.remove(disabled);
    }
    if (cTag != null) {
        cTag.classList.add(disabled);
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

function checkName() {
    var validName = document.querySelector('input[name="name"]');
    var nameRegex = /^[가-힣]{1,5}$/;
    return nameRegex.test(validName.value);
}

function checkPhone() {
    var validPhobe = document.querySelector('input[name="phone"]');
    var phoneRegex = /^(010)-[0-9]{3,4}-[0-9]{4}$/;
    return phoneRegex.test(validPhobe.value);
}

function checkBirth() {
    var validBirth = document.querySelector('input[name="birth"]');
    var birthRegex = /^[0-9]{6}$/;
    return birthRegex.test(validBirth.value);
}

function validEmail(){
    var eEmail = document.querySelector('.errorEmail');
    var cEmail = document.querySelector('.confirmEmail');
    if (!checkEmail()){
        printFalse(eEmail, cEmail, '유효하지 않은 이메일입니다.');
        return
    }
    var emailInput = document.querySelector('input[name="email"]').value
    var emailJson = {email : emailInput };
    fetchPost('/register/check', emailJson, result);
}

function result(map) {
    var eEmail = document.querySelector('.errorEmail');
    var cEmail = document.querySelector('.confirmEmail');
    if (map.status == 'ok') {
        printTrue(eEmail, cEmail, map.message);
        return;
    }
    if (map.status == 'error') {
        alert(map.message);
        printFalse(eEmail, cEmail, map.message);
    }
    return
}

function validPassword() {
    let ePassword = document.querySelector('.errorPassword');
    let cPassword = document.querySelector('.confirmPassword');
    validPasswordCheck();
    if (checkPassword()) {
        printTrue(ePassword, cPassword, '사용가능한 비밀번호 입니다.')
    } else {
        printFalse(ePassword, cPassword, '8~15자의 대,소문자와 특수문자를 1개 이상 작성해주세요.');
    }
}

function validPasswordCheck(){
    var password = document.querySelector('input[name="password"]');
    var passwordCheck = document.querySelector('input[name="passwordCheck"]');

    var ePasswordCheck = document.querySelector('.errorPasswordCheck');
    var cPasswordCheck = document.querySelector('.confirmPasswordCheck');
    if (password.value === passwordCheck.value) {
        printTrue(ePasswordCheck, cPasswordCheck, '비밀번호가 일치합니다.');
    } else {
        printFalse(ePasswordCheck, cPasswordCheck, '비밀번호가 일치하지 않습니다.')
    }
}

function validName(){
    var eName = document.querySelector('.errorName');
    if (checkName()){
        printTrue(eName, null, '')
    } else {
        printFalse(eName, null, '한글만 작성해주세요')
    }
}

function validBirth() {
    var birthday = document.querySelector('input[name="birthday"]');

    var eBirth = document.querySelector('.errorBirth');
    if (checkBirth(birthday.value)) {
        printTrue(eBirth, null, null);
    } else {
        printFalse(eBirth, null, '날짜 형식이 잘못되었습니다.');
    }
    
}

function validPhone() {
    var ePhone = document.querySelector('.errorPhone');
    var cPhone = document.querySelector('.confirmPhone');

    if (checkPhone()){
        printTrue(ePhone, cPhone, '인증번호가 발송되었습니다.');
        clearInterval(timerInterval);
        var timerInterval = limitTimer();
    } else {
        printFalse(ePhone, cPhone, '입력정보가 잘못되었습니다');
    }
}

function limitTimer() {
    let timer = document.querySelector('.limitTime');
    var minutes = 5;
    var seconds = 0;
    var timerInterval = setInterval(function(){
        // 시간 감소
        seconds--;
    
        // 시간이 음수가 되면 분 감소
        if (seconds < 0) {
            minutes--;
            seconds = 59;
        }
        if (minutes == 0) {
            timer.style.color = 'red'
        }
    
        // 분과 초를 2자리 숫자로 표시
        var formattedMinutes = ('0' + minutes).slice(-2);
        var formattedSeconds = ('0' + seconds).slice(-2);
    
        // 타이머 업데이트
        timer.textContent = formattedMinutes + ':' + formattedSeconds;
    
        // 시간이 0이면 타이머 중지
        if (minutes === 0 && seconds === 0) {
            clearInterval(timerInterval);
            alert('타이머 종료!');
            return true;
        }
    }, 1000);
}


function checkBirth(birth) {
    var split = birth.split("-");
    if (split.length != 3) return false;
    for (var i=0;i<split.length;i++) {
        if (!numberCheck(split[i])) {
            return false;
        }
    }
    if (!rangeCheck(split[0], split[1], split[2])) {
        return false;
    }
    return true;
}

function rangeCheck(year, month, day) {
    var date = new Date();
    var nowYear = date.getFullYear();
    var numYear = Number(year);
    var numMonth = Number(month);
    var numDay = Number(day);
    if (numYear < 1920 || numYear > nowYear) {
        return false;
    }
    if (numMonth < 1 || numMonth > 12) {
        return false;
    }
    if (numDay < 1 || numDay > 31) {
        return false;
    }
    date = new Date(numYear, numMonth, 0);
    var lastDay = date.getDate();
    if (numDay > lastDay) {
        return false;
    }
    return true;
}
function numberCheck(num) {
    var numRegex = /[^0-9]/;
    return !numRegex.test(num);
}

function updateTimer(timer) {
    // 시간 감소
    seconds--;

    // 시간이 음수가 되면 분 감소
    if (seconds < 0) {
      minutes--;
      seconds = 59;
    }

    // 분과 초를 2자리 숫자로 표시
    var formattedMinutes = ('0' + minutes).slice(-2);
    var formattedSeconds = ('0' + seconds).slice(-2);

    // 타이머 업데이트
    timerElement.textContent = formattedMinutes + ':' + formattedSeconds;

    // 시간이 0이면 타이머 중지
    if (minutes === 0 && seconds === 0) {
      clearInterval(timerInterval);
      alert("타이머 종료!");
    }
  }