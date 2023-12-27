window.addEventListener('load', () => {
    let detailBtns = document.querySelectorAll('.detailBtn');

    detailBtns.forEach(el => {
        el.addEventListener('click', () => {
            // .matchBox
            let detailBtnChildren = el.children;
            for (let i=0;i<detailBtnChildren.length;i++) {
                if (detailBtnChildren[i].tagName == 'svg') {
                    let svg = detailBtnChildren[i];
                    svg.classList.toggle('rotate');
                }
            }
            let childrens = el.parentElement.parentElement.children;

            for (let i=childrens.length-1;i>=0;i--) {
                if (childrens[i].classList.contains('detailBox')) {
                    let detailBox = childrens[i];
                    detailBox.classList.toggle('show');
                    return;
                }
            }
        })
    })

    search();
})

function search() {
    let startDateinput = document.querySelector('input[name="startDate"]');
    let endDateinput = document.querySelector('input[name="endDate"]');
    let condition = {startDate : startDateinput.value, endDate : endDateinput.value};
    
    fetchPost('/mypage/order/get', condition, matchResult);
}

function getJson(startDateValue, endDateValue) {
    let json = {startDate : startDateValue, 
                endDate : endDateValue};
    return json;
}

function matchResult(list) {

    if (list == null || list.length < 1) {
        searchEmpty();
    } else {
        createList(list);
    }
}

function searchEmpty() {
    let matchList = document.querySelector('.matchList');
    matchList.innerHTML = '<li class="empty"><span>경기 기록이 없어요.</span></li>';
}

function createList(list) {
    let matchList = document.querySelector('.matchList');

    let temp = ''
    for (let i=0;i<list.length;i++) {
        temp += resultForm(list[i]);
    }
    matchList.innerHTML = temp;
}

function resultForm(form) {
    return '<li class="matchBox">' +
                '<span class="matchId">' + form.matchId + '</span>' +
                '<span class="matchDate">' + form.matchDate + '</span>' +
                '<span class="matchTime">' + form.matchHour + '</span>' +
                '<span class="matchMax">' + form.maxPersonAndCount + '</span>' +
                '<a href="/match/' + form.matchId + '" class="title">' + form.fieldTitle + '</a>' +
                '<span>' + form.matchStatus + '</span>' +
                getScore(score) +
            '</li>'
}

function getScore(resultScore) {
    if (resultScore == null) return '<div class="point"></div>';

    let score;
    let className;
    if (score > 0) {
        score = '+ ' + resultScore;
        className = 'win';
    } else if (score < 0) {
        score = '- ' + resultScore;
        className = 'lose';
    } else {
        score = resultScore;
        className = 'draw';
    }
    return '<div class="point ' + className + '">' + score + '</div>';
}