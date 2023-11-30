window.addEventListener('load', function(){
    var region = document.querySelector('.region');
    var gender = document.querySelector('.gender');
    var genderOption = document.querySelector('.genderOption');
    var regionOption = document.querySelector('.regionOption');

    region.addEventListener('click', function(e){
        if (e.target.isEqualNode(region)) {
            var regionOption = document.querySelector('.regionOption');
            addDisabled(regionOption);
            regionOption.classList.toggle('disabled');
        }
    })
    gender.addEventListener('click', function(e){
        if (e.target.isEqualNode(gender)) {
            addDisabled(genderOption);
            genderOption.classList.toggle('disabled');
        }
    })

    document.addEventListener('mouseup', function(e){
        if (!region.contains(e.target)) {
            regionOption.classList.add('disabled');
        }
        if (!gender.contains(e.target)) {
            genderOption.classList.add('disabled');
        }

    })
})

function addDisabled(e) {
    var optionList = document.querySelectorAll('.subOption');
    for (let i=0;i<optionList.length;i++){
        if (!optionList[i].isEqualNode(e)) {
            optionList[i].classList.add('disabled');
        }
    }
}