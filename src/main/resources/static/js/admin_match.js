window.addEventListener('load', function(){
    var region = document.querySelector('.region');
    var regionOption = document.querySelector('.regionOption');

    region.addEventListener('click', function(e){
        addDisabled(regionOption);
        regionOption.classList.toggle('disabled');
        if (regionOption.classList.contains('disabled')) {
            search();
        }
    });
    let searchWord = document.querySelector('input[name="searchWord"]');
    let searchBtn = document.querySelector('#searchBtn');

    //   최초검색
    search();
  
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

    var inputRegion = document.querySelectorAll('input[name="region"]');

    inputRegion.forEach((el) => {
        el.addEventListener('change', (e) => {
            let all = document.querySelector('#regionAll');
            if (all.isEqualNode(e.target)) {
                if (all.checked == true) {
                    let checkRegion = document.querySelectorAll('input[name="region"]:checked');
                    noneChecked(checkRegion, all);
                }
            } else {
                all.checked = false;
            }
        })
    })

})

function noneChecked(list, exceptionNode) {
    list.forEach((el) => {
        if (!el.isEqualNode(exceptionNode)) {
            el.checked = false;
        }
    })
}

function addDisabled(e) {
    var optionList = document.querySelectorAll('.subOption');
    for (let i=0;i<optionList.length;i++){
        if (!optionList[i].isEqualNode(e)) {
            optionList[i].classList.add('disabled');
        }
    }
}

function matchSearch(list) {
    const total = document.querySelector('.total');

    if (list == null || list.length < 1) {
        total.innerHTML = '총 0건';
        searchEmpty();
    } else {
        total.innerHTML = '총 ' + list.length + '건';
        createList(list);
    }
}
function searchEmpty() {
    const searchResult = document.querySelector('.searchResult');
    searchResult.innerHTML = '<li class="none">검색 결과가 없습니다.</li>';
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
    return '<a href="/admin/match/' + searchForm.matchId + '" class="result">' +
                '<span id="matchDate">' + searchForm.matchDate + '</span>' + 
                '<span id="matchTime">' + searchForm.matchTime + '</span>' + 
                '<span id="matchRegion">' + searchForm.matchRegion + '</span>' + 
                '<span id="matchTitle">' + searchForm.matchTitle + '</span>' + 
                '<span id="matchPerson">' + searchForm.matchPerson + '</span>' + 
                '<span id="matchStatus">모집중</span>' + 
            '</a>';
}

function search() {
    let startDate = document.querySelector('input[name="startDate"]')
    let endDate = document.querySelector('input[name="endDate"]')
    let region = document.querySelectorAll('input[name="region"]:checked');
    let searchWord = document.querySelector('input[name="searchWord"]');
    let condition = getJson(startDate.value, endDate.value, getList(region), searchWord.value);
    console.log(condition);
    fetchPost('/admin/match/get', condition, matchSearch);
}
function getJson(startDateValue, endDateValue, array, searchWord) {
    let json = {startDate : startDateValue, 
                endDate : endDateValue, 
                region : array, 
                word : searchWord};
    return json;
}
function getList(node) {
    let array = new Array();
    for (var i = 0;i < node.length;i++) {
        if (node.item(i).id == 'regionAll') {
            array.push(node.item(i).value);
            return array;
        }
    }

    node.forEach(element => {
        array.push(element.value);
    });
    return array;
}