const disabled = 'disabled';
let timerInterval = null;
let clickLimit = null;

window.addEventListener('load', () => {

    const phoneBtn = document.querySelector('#phoneBtn');
    phoneBtn.addEventListener('click', () => {
        if (validPhone() != null) return;

        let phone = document.querySelector('input[name="phone"]').value.replaceAll('-', '');
        let json = {phone : phone};

        sendSMS({result : 'ok'});
        // fetchPost('/sms/send', json, sendSMS);
    })

    const phone = document.querySelector('input[name="phone"]');
    phone.addEventListener('keyup',function(e){

        if (e.key === 'Backspace') {
            phone.value = removePhone(phone.value);
        } else {
            var str = removeNotNumber(phone.value);
            phone.value = addPhone(str);
        }
    })
})





function confirmSMS(result) {
    console.log(result)
    if (result.result == 'ok') {
        var cPhone = document.querySelector('.confirmPhoneCheck');
        printTrue(cPhone, result.message);
        let inputPhone = document.querySelector('input[name="phone"]')
        inputPhone.setAttribute('readonly', true);
        clearInterval(timerInterval);
        clearInterval(clickLimit);
    }
}
function sendSMS(result) {
    console.log(result);
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