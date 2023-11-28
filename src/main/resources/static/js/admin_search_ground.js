const searchResult = document.querySelector('.searchResult');
const total = document.querySelector('.total');

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
    if (list == null || list.length < 1) {
        searchEmpty();
    } else {
        createList(list);
    }
}

function getList(node) {
    let array = new Array();
    for (var i = 0;i < node.length;i++) {
        if (node.item(i).id == 'regionAll') {
            array.push(node.item(i).id);
            return array;
        }
    }

    node.forEach(element => {
        array.push(element.id);
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
    console.log(searchWord);
    let condition = getJson(getList(region), searchWord.value);
    console.log(condition);
    fetchPost('/admin/ground/get', condition, groundSearch);
}

function searchEmpty() {
    total.innerHTML = '총 0건';

    let temp = '<li class="none">검색 결과가 없습니다.</li>';
    searchResult.innerHTML = temp;
}

function createList(list) {
    total.innerHTML = '총 ' + list.length + '건';

    
}
function resultForm(groundId, region, title) {
    return '<a href="/admin/ground/' + groundId + '" class="result"><span id="groundRegion">' + region + '</span><span id="groundTitle">' + title +'</span></a>';
}