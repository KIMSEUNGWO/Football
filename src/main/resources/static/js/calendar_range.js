const date = new Date();
const fixYear = date.getFullYear();
const fixMonth = date.getMonth() + 1;
const fixDay = date.getDate();

let year = date.getFullYear();
let month = date.getMonth() + 1;
let day = date.getDate();

window.addEventListener('load', () => {


    let lastDateMonth = new Date(year, month, 0).getDate() // 현재월의 마지막날
    let startDateMonth = new Date(year, month-1, 1).getDay() // 현재달 시작요일

    let month6 = document.querySelector('#date_picker_6months');
    let month1 = document.querySelector('#date_picker_1months');
    let week1 = document.querySelector('#date_picker_1weeks');

    let preBtn = document.querySelector('#preButton');
    let nextBtn = document.querySelector('#nextButton');

    select(week1);

    let buttons = document.querySelectorAll('.date_picker_buttons button');
    buttons.forEach(el => {
        el.addEventListener('click', () => {
            clearButtons();
            select(el);
            calendar();
            // TODO
            // 검색 Fetch 적용시킬예정
        })
    })

    let renderCalendar = () => {
        let rightMonth = document.querySelector('#rightMonth');
        let rightBody = document.querySelector('#rightBody');
        lender(rightMonth, rightBody, year, month);
    
        let leftMonth = document.querySelector('#leftMonth');
        let leftBody = document.querySelector('#leftBody');
        lender(leftMonth, leftBody, year, month-1);

        if (year == fixYear && month == fixMonth) {
            nextBtn.disabled = true;
        } else {
            nextBtn.disabled = false;
        }
        drawRange();
    }

    renderCalendar();

    preBtn.addEventListener('click', () => {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        renderCalendar();
    })

    nextBtn.addEventListener('click', () => {
        if (month-1 == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }

        renderCalendar();
    })


})

function drawRange() {
    let startDate = document.querySelector('input[name="startDate"]');
    let endDate = document.querySelector('input[name="endDate"]');

    let start = getDate(startDate.value);
    let end = getDate(endDate.value);

    
}

function lender(header, body, year, month) {
    // 2023. 11 형식
    if (month == 0) {
        year--;
        month = 12;
    }
    if (month == 13) {
        year++;
        month = 1;
    }
    header.innerHTML = year + ". " + month;

    // 현재월의 마지막날
    let lastDateMonth = new Date(year, month, 0).getDate();
    // 현재달 시작요일
    let startDateMonth = new Date(year, month-1, 1).getDay();
    

    let temp = '<div class="week"><div class="cell"><div>일</div></div><div class="cell"><div>월</div></div><div class="cell"><div>화</div></div><div class="cell"><div>수</div></div><div class="cell"><div>목</div></div><div class="cell"><div>금</div></div><div class="cell"><div>토</div></div></div></div>';
    let now = 1;

    while (now <= lastDateMonth) {
        temp +=  '<div class="week">' + createWeek(now, lastDateMonth, startDateMonth) + '</div>';
        now += 7 - startDateMonth;
        startDateMonth = 0;
    }
    
    body.innerHTML = temp;
}


function createWeek(now, lastDateMonth, startDateMonth) {
    let temp = '';
    for (let i=startDateMonth;i<7;i++) {
        if (now <= lastDateMonth) {
            temp += '<div class="cell"><button type="button" aria-pressed="false" data-is-today="false">' + now + '</button></div>'
            now++;
        }
    }
    return temp;
}


function clearButtons() {
    let buttons = document.querySelectorAll('.date_picker_buttons button');
    buttons.forEach(el => {
        el.setAttribute('aria-pressed', 'false');    
    })

}

function select(button) {
    let currentState = (button.getAttribute('aria-pressed') === 'true');
    // 현재 상태를 반전시킴
    button.setAttribute('aria-pressed', !currentState);    
    inputDate(button);

}

function inputDate(button) {
    let currentState = (button.getAttribute('aria-pressed') === 'true');
    if (currentState) {
        if (button.id == 'date_picker_1weeks') {
            addDate(0, 7);
        } else if (button.id == 'date_picker_1months') {
            addDate(1, 0);
        } else if (button.id == 'date_picker_6months') {
            addDate(6, 0);
        }
    } else {
        clearDate();
    }
}

function clearDate() {
    let endDate = document.querySelector('input[name="endDate"]');
    let startDate = document.querySelector('input[name="startDate"]');
    endDate.value = ''
    startDate.value = '';
}

function addDate(addMonth, addDay) {
    let date = new Date();
    
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();

    let endDate = document.querySelector('input[name="endDate"]');
    endDate.value = dateForm(year, month, day);

    let newDate = new Date(year, month-addMonth, day-addDay);
    let newYear = newDate.getFullYear();
    let newMonth = newDate.getMonth();
    let newDay = newDate.getDate();

    let startDate = document.querySelector('input[name="startDate"]');
    startDate.value = dateForm(newYear, newMonth, newDay);
}

function getDate(value) {
    let split = value.split("/");
    let array = [];
    array.push(split[0]);
    array.push(split[1]);
    array.push(split[2]);
    return array;
}
function dateForm(year, month, day) {
    return year + '/' + month + '/' + day;
}