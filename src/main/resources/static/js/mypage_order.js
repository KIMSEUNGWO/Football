window.addEventListener('load', () => {
    let detailBtns = document.querySelectorAll('.detailBtn');

    detailBtns.forEach(el => {
        el.addEventListener('click', () => {
            // .matchBox
            let detailBtnChildren = el.children;
            for (let i=0;i<detailBtnChildren.length;i++) {
                if (detailBtnChildren[i].tagName == 'svg') {
                    let svg = detailBtnChildren[i];
                    svg.classList.toggle('rotate');
                }
            }
            let childrens = el.parentElement.parentElement.children;

            for (let i=childrens.length-1;i>=0;i--) {
                if (childrens[i].classList.contains('detailBox')) {
                    let detailBox = childrens[i];
                    detailBox.classList.toggle('show');
                    return;
                }
            }
        })
    })
})