const date = new Date();

const fixYear = date.getFullYear();
const fixMonth = date.getMonth() + 1;
const fixDay = date.getDate();

let year = date.getFullYear();
let month = date.getMonth() + 1;
let day = date.getDate();

window.addEventListener('load', () => {


    let preBtn = document.querySelector('#preButton');
    let nextBtn = document.querySelector('#nextButton');

    let matchDate = document.querySelector('input[name="matchDate"]');
    let calendar = document.querySelector('#date_range_calendar');

    matchDate.addEventListener('click', () => {
        calendar.classList.add('display');
    })

    let renderCalendar = () => {
        let mainMonth = document.querySelector('#mainMonth');
        let mainBody = document.querySelector('#mainBody');
        lender(mainMonth, mainBody, year, month);

        // 이번달 기준 2달 후까지 표시
        let calDate = new Date(year, month-1, 0);
        let compareDate = new Date(fixYear, fixMonth+1, 0);

        if (calDate.getTime() >= compareDate.getTime()) {
            nextBtn.disabled = true;
        } else {
            nextBtn.disabled = false;
        }
        checkToday();
    
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


    this.document.addEventListener('click', (e) => {
        if (e.target.hasAttribute('aria-pressed') && e.target.hasAttribute('data-is-today')) {
            clearRange();
            e.target.setAttribute('aria-pressed', 'true');
            let clickDate = clickAndGetDate(e.target);

            let match = document.querySelector('input[name="matchDate"]');

            let innerDate = dateForm(clickDate.getFullYear(), clickDate.getMonth()+1, clickDate.getDate());

            match.value = innerDate;
            calendar.classList.remove('display');
        }
    })

})

function clickAndGetDate(target) {
    let parentId = target.parentElement.parentElement.parentElement.id;

    let withinDay = Number(target.textContent);
    
    let lMonth = document.querySelector('#mainMonth').textContent;
    let lArray = lMonth.split(". ");
    return new Date(Number(lArray[0]), Number(lArray[1])-1, withinDay);
}

function clearRange() {
    let allBtn = document.querySelectorAll('button[aria-pressed="true"]');
    allBtn.forEach(el => {
        el.setAttribute('aria-pressed', 'false');
    })
}

function checkToday() {
    let mainMonth = document.querySelector('#mainMonth');
    let main = mainMonth.textContent.split(". ");
    if (fixYear == Number(main[0]) && fixMonth == Number(main[1])) {
        drawToday();
    }
}
function drawToday() {
    let selector = '#mainBody button[data-is-today="false"]';
    let cellList = document.querySelectorAll(selector);
    cellList[fixDay-1].setAttribute('data-is-today', 'true');
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
    header.innerHTML = year + ". " + String(month).padStart(2, '0');

    // 현재월의 마지막날
    let lastDateMonth = new Date(year, month, 0).getDate();
    // 현재달 시작요일
    let startDateMonth = new Date(year, month-1, 1).getDay();
    

    let temp = '<div class="week"><div class="cell SUN"><div>일</div></div><div class="cell"><div>월</div></div><div class="cell"><div>화</div></div><div class="cell"><div>수</div></div><div class="cell"><div>목</div></div><div class="cell"><div>금</div></div><div class="cell SAT"><div>토</div></div></div></div>';
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
            if (i == 0) { // 일요일
                temp += '<div class="cell" data-is-within-range="false"><button type="button" aria-pressed="false" data-is-today="false" class="SUN">' + now + '</button></div>'
            }
            else if (i == 6) { // 토요일
                temp += '<div class="cell" data-is-within-range="false"><button type="button" aria-pressed="false" data-is-today="false" class="SAT">' + now + '</button></div>'
            }
            else {
                temp += '<div class="cell" data-is-within-range="false"><button type="button" aria-pressed="false" data-is-today="false">' + now + '</button></div>'
            }
            now++;
        }
    }
    return temp;
}

function clearDate() {
    let matchDate = document.querySelector('input[name="matchDate"]');
    matchDate.value = ''
}

function getDate(value) {
    if (value == '') {
        return null;
    }
    let split = value.split("/");
    let array = [];
    array.push(split[0]);
    array.push(split[1]);
    array.push(split[2]);
    return array;
}
function dateForm(year, month, day) {
    return year + '/' + String(month).padStart(2, '0') + '/' + String(day).padStart(2, '0');
}
function getDateForm(date) {
    let split = date.value.split('/');
    return new Date(Number(split[0]), Number(split[1])-1, Number(split[2]));
}