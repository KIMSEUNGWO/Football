window.addEventListener('load', function(){
    var region = document.querySelector('.region');
    var gender = document.querySelector('.gender');
    var genderOption = document.querySelector('.genderOption');
    var regionOption = document.querySelector('.regionOption');

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

    region.addEventListener('click', function(e){
        if (e.target.isEqualNode(region)) {
            var regionOption = document.querySelector('.regionOption');
            addDisabled(regionOption);
            regionOption.classList.toggle('disabled');
        }
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