window.addEventListener('load', () => {
    const regionBtn = document.querySelector('.region');
    regionBtn.addEventListener('click', () => regionBtn.children.subOption.classList.toggle('show'));

    const regionRadio = document.querySelectorAll('input[name="region"]');
    regionRadio.forEach(el => {
        el.addEventListener('change', () => {
            let subOption = el.parentElement.parentElement;
            subOption.classList.remove('show');
            let title = document.querySelector('.region-title');
            title.innerHTML = el.value;
        });
    })

    const phone = document.querySelector('input[name="phone"]');
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

    const form = document.querySelector('#manager_submit');
    form.addEventListener('click', (e) => {
        let name = document.querySelector('input[name="name"]');
        let region = document.querySelector('input[name="region"]:checked');

        if (name == null || name.value == null || name.value.length < 2) {
            alert('이름을 정확히 입력해주세요.');
            return;
        }
        if (phone == null) {
            alert('활동구역을 선택해주세요');
            return;
        }
        let isConfirm = confirm('매니저 진행을 완료하시겠습니까?');
        if (!isConfirm) return;

        let data = {name : name.value, region : region.value};
        console.log(data);
        fetchPost('/manager/apply/confirm', data, result);

    })

})
function fetchPost(url, json, callback) {
    fetch(url , { method : 'post'
					, headers : {'Content-Type' : 'application/json'}
					, body : JSON.stringify(json)
				})
    .then(res => {
        if (!res.ok) {
            throw Error('연결실패');
        }
        return res.json();
    })
    .then(map => callback(map))
    .catch(error => console.log(error));
}


function result(map) {
    console.log('연결성공');
    console.log(map);
}
