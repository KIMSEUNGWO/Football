window.addEventListener('load', () => {
    const confirmBtn = document.querySelector('#confirmBtn');

    let inputCash = document.querySelectorAll('input[name="cash"]');
    let inputCharge = document.querySelectorAll('input[name="charge"]');
    inputCash.forEach(el => {
        el.addEventListener('change', (e) => {
            clearCashSelect();
            let div = el.parentElement;
            div.classList.add('select');
        })
    })
    console.log(inputCharge)
    inputCharge.forEach(el => {
        el.addEventListener('change', (e) => {
            clearChargeSelect();
            let div = el.parentElement;
            div.classList.add('select');
        })
    })

    let policyLabels = document.querySelectorAll('.checkbox > label');
    let checkBtns = document.querySelectorAll('.check');

    checkBtns.forEach(el => {
        el.addEventListener('click', (e) => {
            if (el.isEqualNode(e.target)) {
                let childrens = el.parentElement.children;
                for (let i=0;i<childrens.length;i++) {
                    let item = childrens.item(i);
                    console.log(item.type);
                    if (item.tagName == 'LABEL') {
                        item.click();
                        return;
                    }
                }
            }
        })
    })

    policyLabels.forEach(el => {
        el.addEventListener('click', e => {
            if (el.isEqualNode(e.target)){
                let childrens = el.parentElement.children;
                for (let i=0;i<childrens.length;i++) {
                    let item = childrens.item(i);
                    console.log(item);
                    if (item.classList.contains('check')) {
                        item.classList.toggle('confirm');
                        return;
                    }
                }
            }
        })
    })

    let checkbox = document.querySelectorAll('.checkbox input[type="checkbox"]');
    checkbox.forEach(el => {
        el.addEventListener('change', e => {
            let checked = document.querySelectorAll('.checkbox input[type="checkbox"]:checked');
            if (checked.length == checkbox.length) {
                confirmBtn.setAttribute('data-is-allowed', "true");
            } else {
                confirmBtn.setAttribute('data-is-allowed', "false");
            }
        })
    })

    confirmBtn.addEventListener('click', (e) => {
        let cash = document.querySelector('input[name="cash"]:checked');
        let charge = document.querySelector('input[name="charge"]:checked');
        let checked = document.querySelectorAll('.checkbox input[type="checkbox"]:checked');

        if (checked.length != checkbox.length) {
            checkbox[0].focus();
            return;
        }
        if (cash == null || charge == null) {
            return;
        }

        let priceValue = cash.value;
        let chargeOption = charge.value;
        if (chargeOption == 'KAKAO') {
            let jsonForm = {price : Number(priceValue)};

            fetchPost("/cash/charge/kakao", jsonForm, result);
        }
    })
    
})
function result(response) {
    location.href = response.next_redirect_pc_url	
}

function clearCashSelect() {
    let nodes = document.querySelectorAll('.cashBtn');
    nodes.forEach(el => {
        el.classList.remove('select');
    });
}

function clearChargeSelect() {
    let nodes = document.querySelectorAll('.chargeBtn');
    nodes.forEach(el => {
        el.classList.remove('select');
    });
}