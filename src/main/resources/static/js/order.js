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

    let couponBtn = document.querySelector('#couponBtn');
    const pop = document.querySelector('.pop');
    couponBtn.addEventListener('click', () => {
        pop.classList.toggle('disabled');
    })

    let popCancel = document.querySelector('#popCancel');
    popCancel.addEventListener('click', () => {
        pop.classList.add('disabled');
    })

    let inputCouponList = document.querySelectorAll('input[name="couponNum"]');
    const displayCoupon = document.querySelector('.calCoupon');
    const totalPrice = document.querySelector('#totalPrice');

    inputCouponList.forEach(el => {
        el.addEventListener('change', () => {
            let code = el.value;
            if (code == '') {
                displayCoupon.innerHTML = '';
            } else {
                let title = document.querySelector('#couponTitle' + code);
                let price = document.querySelector('#couponPrice' + code);
                console.log(title, price);
                displayCoupon.innerHTML = print(title.textContent, price.value);
            }
            calculator(totalPrice);
            pop.classList.add('disabled');
        })
    })

    let customRadio = document.querySelectorAll('.custom-radio');
    customRadio.forEach(el => {
        el.addEventListener('click', () => {
            let parent = el.parentElement.children;
            for (let i=0;i<parent.length;i++) {
                if (parent[i].type == 'radio') {
                    parent[i].click();
                    return;
                }
            }
        })
    })

    let form = document.querySelector('#orderForm');
    form.addEventListener('submit', (e) => {
        console.log('submit');
        let policy = document.querySelector('input[name="policy"]:checked');
        
        if (policy == null) {
            e.preventDefault();
            let error = document.querySelector('.errorMsg');
            error.innerHTML = '약관에 동의해주세요.';
            error.scrollIntoView({behavior : 'smooth'});
            return;
        }
        form.submit();
    })
    
})

function calculator(totalPrice) {
    const matchPrice = document.querySelector('#matchPrice').textContent.replace(/[^0-9]/g, '');
    let coupon = document.querySelector('input[name="couponNum"]:checked');

    let calculate = 0;
    if (coupon != null && coupon.value != '') {
        let price = document.querySelector('#couponPrice' + coupon.value).value;
        calculate += Number(price);
    }
    totalPrice.innerHTML = Math.max(0, (Number(matchPrice) - calculate)).toLocaleString('ko-KR') + '원';
}

function print(title, price) {
    return '<span class="calCouponTitle">' + title + '</span>' +
        '<span class="calCouponPrice">' + (-1 * Number(price)).toLocaleString('ko-KR') + '원</span>'
}