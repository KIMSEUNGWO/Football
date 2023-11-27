window.addEventListener('load', function(){
    const bar = document.querySelector('.progress-bar');

    let grade1 = 10;
    let grade2 = 20;
    let grade3 = 70;
    let t = 0;

    const barAnimation = setInterval(() => {
        if (grade1 >= t) {
            bar.style.background = `linear-gradient(to right, var(--grade1) 0 ${t}%, #dedede ${t}% 100%)`;
        }
        else if (grade1 + grade2 >= t) {
            bar.style.background = `linear-gradient(to right, var(--grade1) 0 ${grade1}%, var(--grade2) ${grade1}% ${t}%, #dedede ${t}% 100%)`;
        }
        else if (grade1 + grade2 + grade3 >= t) {
            bar.style.background = `linear-gradient(to right, var(--grade1) 0 ${grade1}%, var(--grade2) ${grade1}% ${grade1+grade2}%, var(--grade3) ${grade1+grade2}% ${t}%, #dedede ${t}% 100%)`;
        }
        if (t++ >= grade1 + grade2 + grade3) {
            this.clearInterval(barAnimation)
        }
      }, 10)
})