window.addEventListener('load', () => {

    const addMatchBtn = document.querySelector('.addMatch');

    addMatchBtn.addEventListener('click', () => {
            const matchPlaying = addMatchBtn.parentElement.parentElement;
            const matchList = matchPlaying.querySelector('.matchList');
            matchList.appendChild(matchForm());
    });

    // 이벤트리스터 위임
    let matchPlaying = document.querySelectorAll('.matchList');
    matchPlaying.forEach(el => {
        el.addEventListener('click', (e) => {
            let target = e.target;
            if (target.classList.contains('selector')) {
                selectColor(target);
                return;
            }
            if (target.classList.contains('remove')) {
                remove(target);
                return;
            }

            if (target.classList.contains('inc') || target.classList.contains('dec')) {
                const matchScore = target.parentElement.parentElement.querySelector('.match-score');
                console.log(matchScore);
                let value = Number(matchScore.textContent);
                if (target.classList.contains('inc')) {
                    matchScore.innerHTML = value + 1;
                } else {
                    matchScore.innerHTML = Math.max(0, value - 1);
                }
            }

        })
    })


})

function getScore(target, arrow) {
    let resultDetail = target.parentElement.parentElement.parentElement;
    if (arrow == 'left') {
        return resultDetail.querySelector('.left-score-record');
    }
    if (arrow == 'right') {
        return resultDetail.querySelector('.right-score-record');
    }
    
}
function remove(target) {
    let parent = target.parentElement.parentElement.parentElement
    if (parent.classList.contains('match-result-wrap')) {
        parent.remove();
    }
}
function abled(warp, arrow) {
    let scoreRecord = getScoreRecord(warp, arrow);
    let addBtn = scoreRecord.querySelector('.addBtn');
    addBtn.disabled = false;
}
function getScoreRecord(warp, arrow) {
    if (arrow == 'left') {
        return warp.querySelector('.score-record.left-score-record');
    }
    return warp.querySelector('.score-record.right-score-record');
}
function selectColor(target) {
    let teamName = target.parentElement.parentElement;
    let matchTeam = teamName.parentElement;

    let resultMatch = matchTeam.parentElement;

    let isDistinct = distinctCheck(target, resultMatch);

    if (isDistinct) {
        alert('같은 팀은 선택할 수 없습니다');
        return;
    }
    console.log(teamName.parentElement);

    if (target.classList.contains('red')) {
        matchTeam.classList.add('red');
        teamName.innerHTML = 'RED';
    } else if (target.classList.contains('blue')) {
        matchTeam.classList.add('blue');
        teamName.innerHTML = 'BLUE';
    } else if (target.classList.contains('yellow')) {
        matchTeam.classList.add('yellow');
        teamName.innerHTML = 'YELLOW';
    }

}
function distinctCheck(target, resultMatch) {
    let color = (target.classList.contains('red')) ? 'RED' : (target.classList.contains('blue')) ? 'BLUE' : (target.classList.contains('yellow')) ? 'YELLOW' : null;
    let left = resultMatch.querySelector('.left-team .team-name').textContent;
    let right = resultMatch.querySelector('.right-team .team-name').textContent;

    return (color == left || color == right);
}
function matchForm() {
    const resultWrap = document.createElement('div');
    resultWrap.classList.add('match-result-wrap');

    const matchResult = document.createElement('div');
    matchResult.classList.add('match-result');

    const matchTeamLeft = createMatchTeam('left');
    const middleBox = createMiddleBox();
    const matchTeamRight = createMatchTeam('right');

    matchResult.appendChild(matchTeamLeft);
    matchResult.appendChild(middleBox);
    matchResult.appendChild(matchTeamRight);

    resultWrap.appendChild(matchResult);

    return resultWrap;
}
function matchResultDetail() {
    let resultDetail = document.createElement('div');
    resultDetail.classList.add('match-result-detail');

    let leftScore = createScore('left');
    let rightScore = createScore('right');

    resultDetail.appendChild(leftScore);
    resultDetail.appendChild(rightScore);

    return resultDetail;
}
function createScore(arrow) {
    let scoreRecord = document.createElement('ul');
    scoreRecord.classList.add('score-record');

    let goal = createGoal(arrow);

    if (arrow == 'left') {
        scoreRecord.classList.add('left-score-record');
    } else {
        scoreRecord.classList.add('right-score-record');
    }
    scoreRecord.appendChild(goal);
    return scoreRecord;
}


function createMiddleBox() {
    let middle = document.createElement('div');
    middle.classList.add('middle-box');

    let btn = document.createElement('button');
    btn.type = 'button';
    btn.classList.add('remove');
    btn.innerHTML = '제거';
    
    middle.appendChild(btn);
    return middle;
}
function createMatchTeam(arrow) {
    let teamName = createTeamName();
    let detail = createDetail(arrow);

    let matchTeam = document.createElement('div');
    matchTeam.classList.add('match-team');
    if (arrow == 'left') {
        matchTeam.classList.add('left-team');
        matchTeam.appendChild(teamName)
        matchTeam.appendChild(detail);
    } else {
        matchTeam.classList.add('right-team');
        matchTeam.appendChild(detail);
        matchTeam.appendChild(teamName)
    }
    return matchTeam;
}
function createTeamName() {
    let teamName = document.createElement('div');
    teamName.classList.add('team-name');

    let selectColor = document.createElement('div');
    selectColor.classList.add('selectColor');

    let redBtn = document.createElement('button');
    redBtn.type = 'button';
    redBtn.classList.add('selector');
    redBtn.classList.add('red');
    redBtn.innerHTML = 'R';
    let blueBtn = document.createElement('button');
    blueBtn.type = 'button';
    blueBtn.classList.add('selector');
    blueBtn.classList.add('blue');
    blueBtn.innerHTML = 'B';
    let yellowBtn = document.createElement('button');
    yellowBtn.type = 'button';
    yellowBtn.classList.add('selector');
    yellowBtn.classList.add('yellow');
    yellowBtn.innerHTML = 'Y';

    selectColor.appendChild(redBtn);
    selectColor.appendChild(blueBtn);
    selectColor.appendChild(yellowBtn);
    teamName.appendChild(selectColor);
    return teamName;
}
function createDetail(arrow) {
    let teamDetail = document.createElement('div');
    teamDetail.classList.add('team-detail');

    let matchScore = document.createElement('div');
    matchScore.classList.add('match-score');
    matchScore.innerHTML = '0';

    let arrowBtn = createArrow();

    if (arrow == 'left') {
        teamDetail.classList.add('left-team-detail');
        matchScore.classList.add('left-score');
        teamDetail.appendChild(arrowBtn);
        teamDetail.appendChild(matchScore);
    } else {
        teamDetail.classList.add('right-team-detail');
        matchScore.classList.add('right-score');
        teamDetail.appendChild(matchScore);
        teamDetail.appendChild(arrowBtn);
    }

    return teamDetail;
}

function createArrow() {
    // Arrow 컨테이너 생성
    let arrowContainer = document.createElement('div');
    arrowContainer.className = 'arrow';

    // 증가 버튼 생성 및 설정
    let incButton = document.createElement('button');
    incButton.type = 'button';
    incButton.className = 'inc';
    // SVG 생성 및 설정 (증가 버튼용)
    let incSvg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    incSvg.setAttribute('style', 'rotate: 270deg;');
    incSvg.setAttribute('viewBox', '0 0 320 512');
    // SVG Path 생성 및 설정
    let incPath = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    incPath.setAttribute('d', 'M278.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-160 160c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L210.7 256 73.4 118.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l160 160z');
    incSvg.appendChild(incPath);
    incButton.appendChild(incSvg);

    // 감소 버튼 생성 및 설정
    let decButton = document.createElement('button');
    decButton.type = 'button';
    decButton.className = 'dec';
    // SVG 생성 및 설정 (감소 버튼용)
    let decSvg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    decSvg.setAttribute('style', 'rotate: 90deg;');
    decSvg.setAttribute('viewBox', '0 0 320 512');
    // SVG Path 생성 및 설정
    let decPath = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    decPath.setAttribute('d', 'M278.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-160 160c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L210.7 256 73.4 118.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l160 160z');
    decSvg.appendChild(decPath);
    decButton.appendChild(decSvg);

    // 버튼을 컨테이너에 추가
    arrowContainer.appendChild(incButton);
    arrowContainer.appendChild(decButton);

    return arrowContainer;
}