
window.addEventListener('load', () => {
    let region = document.querySelector('.regionBox');
    let regionOption = document.querySelector('.regionOption');
    let regionName = document.querySelector('#regionName');

    region.addEventListener('click', function(e){
            let regionOption = document.querySelector('.regionOption');
            regionOption.classList.toggle('disabled');
    })

    document.addEventListener('mouseup', function(e){
        if (!region.contains(e.target)) {
            regionOption.classList.add('disabled');
        }
    })

    let regionRadio = document.querySelectorAll('input[name="region"]');
    regionRadio.forEach((el) => {
        el.addEventListener('change', (e) => {
            let name = document.querySelector('label[for="' + e.target.id + '"]');
            regionName.innerHTML = name.textContent;
            regionOption.classList.add('disabled');
        })
    })


    let size = document.querySelectorAll('.size');
    size.forEach(el => {
        el.addEventListener('keyup', e => {
            if (!isNumber(el.value)) {
               el.value = '';
               return;
            }
            // 두자리수까지 허용
            if (el.value > 2) {
                el.value = el.value.slice(0, 2);
            }
        })
    })

})

function isNumber(value) {
    return !isNaN(value) && value != '';
}
