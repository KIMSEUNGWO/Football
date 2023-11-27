window.addEventListener('load', function(){
    
    let policy1 = document.querySelector('#policyBtn1');
    let policy2 = document.querySelector('#policyBtn2');
    let policyBtn = document.querySelector('.check');
    let policyLabel = document.querySelector('label[for="policy"]')

    policyLabel.addEventListener('click', () => {
        var checkBox = document.querySelector('.check');
        checkBox.classList.toggle('confirm');
    })

    policyBtn.addEventListener('click', (e)=>{
            var checkBox = document.querySelector('.check');
            var inputCheckbox = document.querySelector('input[name="policy"]');
            if (inputCheckbox.checked) {
                inputCheckbox.checked = false;
                checkBox.classList.remove('confirm');
            } else {
                inputCheckbox.checked = true;
                checkBox.classList.add('confirm');
            }

    })

    policy1.addEventListener('click', (e)=>{
        if (policy1.isEqualNode(e.target)) {
            let arrow1 = document.querySelector('.policy1svg');
            arrow1.classList.toggle('rotate');
            let policyTxt1 = document.querySelector('#pText1');
            policyTxt1.classList.toggle('show');
        }
    })
    policy2.addEventListener('click', (e)=>{
        if (policy2.isEqualNode(e.target)) {
            let arrow2 = document.querySelector('.policy2svg');
            arrow2.classList.toggle('rotate');
            let policyTxt2 = document.querySelector('#pText2');
            policyTxt2.classList.toggle('show');
        }
    })
    
})