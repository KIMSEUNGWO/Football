window.addEventListener('load', function(){
    const limitCount = 13; // 오늘부터 13일 후까지 표시할 예정

    const calendar = document.querySelector(".calendar");
    const week = {
        SUN : 0,
        MON : 1,
        TUE : 2,
        WED : 3,
        THU : 4,
        FRI : 5,
        SAT : 6,
    }
    rotate(limitCount, calendar);




})

function rotate(limitCount, calendar) {
    let date = new Date();

    for (let i = 0; i < limitCount ; i++) {
        drawing(date.getDate(), date.getDay(), calendar);
        date.setDate(date.getDate() + 1);
    }
    return;
}

function drawing(date, week, calendar) {
    // calendar.innerHTML += date + " : " + week;
    // calendar.innerHTML += " ::::";
    return;
}