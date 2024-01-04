window.addEventListener('load', () => {
    let mobileMenu = document.querySelector('#mobileMenu');
    let html = document.querySelector('html');
    mobileMenu.addEventListener('click', () => {
        html.classList.toggle('active');
    })

    let overlay = document.querySelector('#overlay');
    overlay.addEventListener('click', () => {
        html.classList.remove('active');
    })
})