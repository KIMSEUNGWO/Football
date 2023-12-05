const level = ['루키', '스타터', '비기너', '아마추어','세미프로','프로'];

window.addEventListener('load', () => {
    const inputLeft = document.querySelector('#gradeLeft');
    const inputRight = document.querySelector('#gradeRight');

    const thumbLeft = document.querySelector('.thumb.left');
    const thumbRight = document.querySelector('.thumb.right');

    const range = document.querySelector('.range');

    let setLeftValue = () => {
        percentCalculatorLeft(inputLeft, range, thumbLeft);
    };

    let setRightValue = () => {
        percentCalculatorRight(inputRight, range, thumbRight);
    };

    setLeftValue();
    setRightValue();
});

function percentCalculatorRight(input, range, thumbRight) {
    let after = document.querySelector('.thumbRightTitle');
    changeTitle(after, input);

    let max = input.max;
    console.log(input.value)
    let cal = (100 / max * input.value);
    if (cal >= 100) {
        thumbRight.style.left = null;

        range.style.right = '0%';
        thumbRight.style.right = '0%';
        thumbRight.style.zIndex = '10';
        return;
    }

    thumbRight.style.left = null;

    range.style.right = 100 - cal - 5 + '%';
    thumbRight.style.left = cal + '%';
    thumbRight.style.zIndex = '1';
}

function percentCalculatorLeft(input, range, thumbLeft) {
    let after = document.querySelector('.thumbLeftTitle');
    changeTitle(after, input);

    let max = input.max;
    let cal = 100 / max * input.value;
    if (cal >= 100) {
        range.style.left = null;
        thumbLeft.style.left = null;

        range.style.right = '0%';
        thumbLeft.style.right = '0%';
        return;
    }
    thumbLeft.style.right = null;

    range.style.left = cal + '%';
    thumbLeft.style.left = cal + '%';
}

function handleDrag(event, input, setValueFunction) {
    let sliderRect = document.querySelector('.slider').getBoundingClientRect();

    let cal = ((event.clientX - dragInitial) / sliderRect.width) * 100
    cal = Math.floor(cal) / (100 / input.max);
    cal = Math.floor(cal);
    input.value = checkValue(cal, input);
    setValueFunction();
}

function checkValue(cal, input) {
    let id = input.id;
    if (id == 'gradeLeft') {
        let right = Number(document.querySelector('#gradeRight').value);
        return Math.min(initialValue+cal, right);
    }
    if (id == 'gradeRight') {
        let left = Number(document.querySelector('#gradeLeft').value);
        return Math.max(initialValue+cal, left);
    }
    return initialValue + cal;
}

function changeTitle(after, input) {
    let value = Number(input.value);
    if (value == 0 || value == level.length-1) {
        after.innerHTML = '';
        return;
    }
    let nowLevel = level[value];
    after.innerHTML = nowLevel;
}
