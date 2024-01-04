window.addEventListener('load', () => {
    let mapsBtn = document.querySelector('.fieldAddress');
    let maps = document.querySelector('#maps');
    mapsBtn.addEventListener('click', () => {
        maps.scrollIntoView({behavior : 'smooth'});
        maps.classList.toggle('disabled');
        let mapBox = document.querySelector('#map');
        if (mapBox.children.length == 0) {
            showMaps(mapsBtn.textContent);
        }
    })
})

function showMaps(address) {
    naver.maps.Service.geocode({ query: address }, function(status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
            return alert('Something wrong!');
        }

        let result = response.v2; // 검색 결과의 컨테이너
        let x = Number(result.addresses[0].x);
        let y = Number(result.addresses[0].y);
        var map = new naver.maps.Map('map', {
            center: new naver.maps.LatLng(y, x),
            zoom: 15
        });
        
        var marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(y, x),
            map: map
        });
        
    });
    
}