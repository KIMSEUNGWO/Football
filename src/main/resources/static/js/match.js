window.addEventListener('load', function(){
    let managerConfirm = document.querySelector('#managerBtn:not(.already)');

    managerConfirm.addEventListener('click', () => {
        const currentURL = window.location.href;
        const matchResult = currentURL.match(/\/match\/(\d+)/);
        if (!matchResult) {
            this.location.reload();
        }

        const matchIdValue = matchResult[1];
        let check = window.confirm('매니저로 신청하시겠습니까?');
        if (check) {
            // let json = {matchIdStr : matchIdValue};
            let json = matchIdValue;
            fetchPost('/manager/apply', json, result);
        }
    })

    let detail = this.document.querySelector('#detail');
    detail.addEventListener('click', () => {
        let a = this.document.querySelectorAll('.fieldMain .matchDate, .fieldMain .matchLocation, .fieldMain .sideHr');
        a.forEach(el => el.classList.toggle('show'));

        let svg = this.document.querySelector('#detail svg');
        svg.classList.toggle('show');
    })
})

function result(map) {
    console.log(map);
    alert(map.message);
    if (map.result == 'ok') {
        this.location.reload();
    }
    if (map.result == 'NotLogin') {
        this.location.href = '/login';
    }
}