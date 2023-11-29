window.addEventListener('load', ()=>{
  let searchWord = document.querySelector('input[name="searchWord"]');
  let searchBtn = document.querySelector('#searchBtn');

  let region = document.querySelectorAll('input[name="region"]:checked');

//   최초검색
  search();

  searchWord.addEventListener('keyup', (e)=>{
    if (searchWord.isEqualNode(e.target)) {
        if (e.key == 'Enter') {
            search();
        }
    }
  })

  searchBtn.addEventListener('click', (e)=>{
    if (searchBtn.isEqualNode(e.target)) {
        search();
    }
  })
  var regionBox = document.querySelector('.region');
  var regionOption = document.querySelector('.regionOption');
  this.document.addEventListener('mouseup', function(e){
    if (!regionBox.contains(e.target)) {
        regionOption.classList.add('disabled');
        search();
    }
})
})

function groundSearch(list) {
    const total = document.querySelector('.total');

    if (list == null || list.length < 1) {
        total.innerHTML = '총 0건';
        searchEmpty();
    } else {
        total.innerHTML = '총 ' + list.length + '건';
        createList(list);
    }
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

function getJson(array, searchWord) {
    let json = {region : array, word : searchWord};
    return json;
}

function search() {
    var region = document.querySelectorAll('input[name="region"]:checked');
    var searchWord = document.querySelector('input[name="searchWord"]');
    let condition = getJson(getList(region), searchWord.value);
    fetchPost('/admin/ground/get', condition, groundSearch);
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
    return '<a href="/admin/ground/' + searchForm.fieldId + '" class="result"><span id="groundRegion">' + searchForm.region + '</span><span id="groundTitle">' + searchForm.fieldName +'</span></a>';
}