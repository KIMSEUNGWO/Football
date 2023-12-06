window.addEventListener('load', function(){
    let region = document.querySelector('.region');
    let gender = document.querySelector('.gender');
    let grade = document.querySelector('.grade');

    let genderOption = document.querySelector('.genderOption');
    let regionOption = document.querySelector('.regionOption');
    let gradeOption = document.querySelector('.gradeOption');

    region.addEventListener('click', function(e){
        if (e.target.isEqualNode(region)) {
            let regionOption = document.querySelector('.regionOption');
            addDisabled(regionOption);
            regionOption.classList.toggle('disabled');
            if (regionOption.classList.contains('disabled')) {
                search();
            }
        }
    })
    gender.addEventListener('click', function(e){
        if (e.target.isEqualNode(gender)) {
            addDisabled(genderOption);
            genderOption.classList.toggle('disabled');
            if (genderOption.classList.contains('disabled')) {
                search();
            }
        }
    })
    grade.addEventListener('click', function(e){
        if (e.target.isEqualNode(grade)) {
            addDisabled(gradeOption);
            gradeOption.classList.toggle('disabled');
            if (gradeOption.classList.contains('disabled')) {
                search();
            }
        }
    })

    let regionRadio = document.querySelectorAll('input[name="region"]');
    regionRadio.forEach(el => {
        el.addEventListener('change', () => {
            let span = document.querySelector('button.region > span');
            if (el.id == 'regionAll') {
                span.innerHTML = '지역';
            } else {
                let labelText = document.querySelector('label[for="' + el.id + '"]').textContent;
                span.innerHTML = labelText;
            }
        })
    })
    let genderRadio = document.querySelectorAll('input[name="gender"]');
    genderRadio.forEach(el => {
        el.addEventListener('change', () => {
            let span = document.querySelector('button.gender > span');
            if (el.id == 'genderAll') {
                span.innerHTML = '성별';
            } else {
                let labelText = document.querySelector('label[for="' + el.id + '"]').textContent;
                span.innerHTML = labelText;
            }
        })
    })
    let gradeRadio = document.querySelectorAll('input[name="grade"]');
    gradeRadio.forEach(el => {
        el.addEventListener('change', () => {
            let span = document.querySelector('button.grade > span');
            if (el.id == 'gradeAll') {
                span.innerHTML = '등급';
            } else {
                let labelText = document.querySelector('label[for="' + el.id + '"]').textContent;
                span.innerHTML = labelText;
            }
        })
    })

    this.document.addEventListener('mouseup', function(e){
        if (!region.contains(e.target)) {
            regionOption.classList.add('disabled');
        }
        if (!gender.contains(e.target)) {
            genderOption.classList.add('disabled');
        }
        if (!grade.contains(e.target)) {
            gradeOption.classList.add('disabled');
        }
    })

    let searchWord = document.querySelector('input[name="searchWord"]');
    let searchBtn = document.querySelector('.searchButton');
    searchWord.addEventListener('keyup', (e)=>{
        if (searchWord.isEqualNode(e.target)) {
            if (e.key == 'Enter') {
                search();
            }
        }
    })

    searchBtn.addEventListener('click', ()=>{
        search();
    })
    search();
})

function addDisabled(e) {
    let optionList = document.querySelectorAll('.subOption');
    for (let i=0;i<optionList.length;i++){
        if (!optionList[i].isEqualNode(e)) {
            optionList[i].classList.add('disabled');
        }
    }
}

function search() {
    let matchDate = document.querySelector('input[name="matchDate"]').value;
    let region = document.querySelector('input[name="region"]:checked').value;
    let gender = document.querySelector('input[name="gender"]:checked').value;
    let grade = document.querySelector('input[name="grade"]:checked').value;
    let searchWord = document.querySelector('input[name="searchWord"]').value;

    let condition = getJson(matchDate, region, gender, grade, searchWord);
    console.log(condition);
    fetchPost('/get', condition, mainSearch);
}
function getJson(matchDateValue, regionValue, genderValue, gradeValue, searchWord) {
    let json = {matchDate : matchDateValue, 
                region : regionValue, 
                gender : genderValue, 
                grade : gradeValue,
                word : searchWord,
            };
    return json;
}

function mainSearch(list) {
    let total = document.querySelector('.searchCount');
    let date = document.querySelector('.searchDate');
    let matchDate = document.querySelector('input[name="matchDate"]');
    date.innerHTML =  dateFormValue(matchDate.value);
    if (list == null || list.length < 1) {
        total.innerHTML = '총 0건';
        searchEmpty();
    } else {
        total.innerHTML = '총 ' + list.length + '건';
        createList(list);
    }
}

function dateFormValue(value) {
    let split = value.split('/');
    return split[0] + '년 ' + split[1] + '월 ' + split[2] + '일';
}


function searchEmpty() {
    const searchResult = document.querySelector('.searchResult');
    searchResult.innerHTML = '<div class="btnBox notFound"><span>검색 결과가 없습니다.</span></div>';
}

function createList(list) {
    const searchResult = document.querySelector('.searchResult');

    let temp = ''
    for (let i=0;i<list.length;i++) {
        temp += resultForm(list[i]);
    }
    searchResult.innerHTML = temp;
}

function resultForm(searchForm) {
    return '<a href="/match/' + searchForm.matchId + '" class="result btnBox">' +
                '<span class="resultHour">' + searchForm.matchHour + '</span>' +
                '<span class="resultRegion">' + searchForm.matchRegion + '</span>' +
                '<div class="resultContent">' +
                    '<div class="resultTitle">' + searchForm.matchTitle + '</div>' +
                    '<ul class="subTitle">' +
                        '<li class="resultGender">' + searchForm.matchGender + '</li>' +
                        '<li class="resultGrade">' + searchForm.matchGrade + '</li>' +
                        '<li class="resultPlayer">'+ searchForm.matchMaxPerson + '</li>' +
                    '</ul>' +
                '</div>' +
                '<button class="resultButton on">신청하기</button>' +
            '</a>';
}