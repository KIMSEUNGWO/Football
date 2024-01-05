window.addEventListener('load', () => {

    const addMatchBtnList = document.querySelectorAll('.addMatch');
    let selectorList = document.querySelectorAll('.selector');

    // 변수
    let targets;
    let score;
    let teamScore;
    let arrow;

    addMatchBtnList.forEach(btn => {
        btn.addEventListener('click', () => {
            let matchPlaying = btn.parentElement.parentElement;
            let matchList = matchPlaying.querySelector('.matchList');
            matchList.appendChild(matchForm());
        })
    })

    let playerWrap = document.querySelector('.playerWrap');
    playerWrap.addEventListener('click', (e) => {
        if (e.target.classList.contains('playerWrap')) {
            playerWrap.classList.add('disabled');
            playerWrap.children.forEach(el => el.classList.add('disabled'));
        }
    })

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

            if (target.classList.contains('addPlayer')) {
                let json = addGoal(target);
                targets = json.target;
                arrow = json.arrow;
                score = json.score;
                teamScore = json.teamScore;
            }
            
            if (target.classList.contains('removePlayer')) {
                refreshScore(target);
                target.parentElement.remove();
            }
        })
    })

    let applyBtn = document.querySelectorAll('.applyGoal');
    applyBtn.forEach(el => {
        el.addEventListener('click', (e) => {
            let list = el.parentElement.parentElement;
            let checked = list.querySelector('input[type="radio"]:checked');
            if (checked == null) {
                alert('선수를 선택해주세요');
                return;
            }
            
            let playerId = checked.value;
            let playerName = getPlayerName(checked);
            let time = el.parentElement.querySelector('input[name="time"]').value;
    
            let json =  {id : playerId, name : playerName, time : time};
            let goalTag = getGoal(json, arrow);

            score.insertBefore(goalTag, targets);

            teamScore.innerHTML = score.children.length - 1

            let colorList = document.querySelectorAll('.matchPlayer .playerColor');
            let playerWrap = document.querySelector('.playerWrap'); 
            playerWrap.classList.add('disabled');
            colorList.forEach(el => el.classList.add('disabled'));
            targets = '';
            score = '';
            goalTag = '';
        })
    })

    let radioList = document.querySelectorAll('input[type="radio"]');
    radioList.forEach(radio => {
        radio.addEventListener('change', () => {
            let label = radio.parentElement;
            let labelList = document.querySelectorAll('label[aria-pressed="true"]');
            labelList.forEach(element => {
                element.setAttribute('aria-pressed', 'false');
            })
            label.setAttribute('aria-pressed', 'true');
        })
    })


})
function refreshScore(target) {
    let record = target.parentElement.parentElement;
    let refreshScore = record.children.length - 2; // 삭제되기 전이기때문에 -2

    console.log(record.children.length);
    let arrow = getArrow(target);
    let teamScore = getTeamScore(target, arrow);
    teamScore.innerHTML = refreshScore;
}
function getPlayerName(checked) {
    let label = checked.parentElement;
    return label.querySelector('.colorPlayerName').textContent;
}
function addGoal(target) {
    let arrow = getArrow(target);
    let score = getScore(target, arrow);
    let color = getColor(target, arrow);
    let teamScore = getTeamScore(target, arrow);

    let isSelectColor = isSelect(color);
    if (isSelectColor == null) return;

    let playerColor = getPlayerColor(isSelectColor);
    if (playerColor == null) return;
    let colorList = document.querySelectorAll('.matchPlayer .playerColor');
    colorList.forEach(el => {
        el.classList.add('disabled');
        el.parentElement.classList.add('disabled');
    });

    playerColor.parentElement.classList.remove('disabled');
    playerColor.parentElement.parentElement.classList.remove('disabled');
    return {target : target.parentNode, score : score, arrow : arrow, teamScore : teamScore};
}
function getTeamScore(target, arrow) {
    let parent = target.parentElement.parentElement.parentElement.parentElement;
    let score = parent.querySelector('.' + arrow + '-score');
    return score;
}
function getGoal(json, arrow) {
    let li = document.createElement('li');
    li.classList.add('goal');

    if (arrow == 'left') {
        li.classList.add('left-goal');
        li.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" class="removePlayer" viewBox="0 0 512 512"><path d="M256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM184 232H328c13.3 0 24 10.7 24 24s-10.7 24-24 24H184c-13.3 0-24-10.7-24-24s10.7-24 24-24z"/></svg>' + 
                        '<input type="text" name="player" value="' + json.id + '" hidden>' + 
                        '<span class="goal-player">' + json.name + '</span>' + 
                        '<b>' + json.time + '’</b>' +
                        '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M417.3 360.1l-71.6-4.8c-5.2-.3-10.3 1.1-14.5 4.2s-7.2 7.4-8.4 12.5l-17.6 69.6C289.5 445.8 273 448 256 448s-33.5-2.2-49.2-6.4L189.2 372c-1.3-5-4.3-9.4-8.4-12.5s-9.3-4.5-14.5-4.2l-71.6 4.8c-17.6-27.2-28.5-59.2-30.4-93.6L125 228.3c4.4-2.8 7.6-7 9.2-11.9s1.4-10.2-.5-15l-26.7-66.6C128 109.2 155.3 89 186.7 76.9l55.2 46c4 3.3 9 5.1 14.1 5.1s10.2-1.8 14.1-5.1l55.2-46c31.3 12.1 58.7 32.3 79.6 57.9l-26.7 66.6c-1.9 4.8-2.1 10.1-.5 15s4.9 9.1 9.2 11.9l60.7 38.2c-1.9 34.4-12.8 66.4-30.4 93.6zM256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zm14.1-325.7c-8.4-6.1-19.8-6.1-28.2 0L194 221c-8.4 6.1-11.9 16.9-8.7 26.8l18.3 56.3c3.2 9.9 12.4 16.6 22.8 16.6h59.2c10.4 0 19.6-6.7 22.8-16.6l18.3-56.3c3.2-9.9-.3-20.7-8.7-26.8l-47.9-34.8z"/></svg>'
    }
    if (arrow == 'right') {
        li.classList.add('right-goal');
        li.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M417.3 360.1l-71.6-4.8c-5.2-.3-10.3 1.1-14.5 4.2s-7.2 7.4-8.4 12.5l-17.6 69.6C289.5 445.8 273 448 256 448s-33.5-2.2-49.2-6.4L189.2 372c-1.3-5-4.3-9.4-8.4-12.5s-9.3-4.5-14.5-4.2l-71.6 4.8c-17.6-27.2-28.5-59.2-30.4-93.6L125 228.3c4.4-2.8 7.6-7 9.2-11.9s1.4-10.2-.5-15l-26.7-66.6C128 109.2 155.3 89 186.7 76.9l55.2 46c4 3.3 9 5.1 14.1 5.1s10.2-1.8 14.1-5.1l55.2-46c31.3 12.1 58.7 32.3 79.6 57.9l-26.7 66.6c-1.9 4.8-2.1 10.1-.5 15s4.9 9.1 9.2 11.9l60.7 38.2c-1.9 34.4-12.8 66.4-30.4 93.6zM256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zm14.1-325.7c-8.4-6.1-19.8-6.1-28.2 0L194 221c-8.4 6.1-11.9 16.9-8.7 26.8l18.3 56.3c3.2 9.9 12.4 16.6 22.8 16.6h59.2c10.4 0 19.6-6.7 22.8-16.6l18.3-56.3c3.2-9.9-.3-20.7-8.7-26.8l-47.9-34.8z"/></svg>' +
                        '<input type="text" name="player" value="' + json.id + '" hidden>' + 
                        '<b>' + json.time + '</b>' +
                        '<span class="goal-player">' + json.name + '</span>' +
                        '<svg xmlns="http://www.w3.org/2000/svg" class="removePlayer" viewBox="0 0 512 512"><path d="M256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM184 232H328c13.3 0 24 10.7 24 24s-10.7 24-24 24H184c-13.3 0-24-10.7-24-24s10.7-24 24-24z"/></svg>'
    }
    return li;
}
function getPlayerColor(color) {
    let matchPlayer = document.querySelector('.matchPlayer');
    if (color == 'red') {
        return matchPlayer.querySelector('.color.red');
    }
    if (color == 'blue') {
        return matchPlayer.querySelector('.color.blue');
    }
    if (color == 'yellow') {
        return matchPlayer.querySelector('.color.yellow');
    }
    return null;
}
function isSelect(color) {
    if (color.classList.contains('red')) {
        return 'red';
    }
    if (color.classList.contains('blue')) {
        return 'blue';
    }
    if (color.classList.contains('yellow')) {
        return 'yellow';
    }
    alert('먼저 팀을 선택해주세요');
    return null;
}
function getColor(target, arrow) {
    let wrap = target.parentElement.parentElement.parentElement.parentElement
    if (arrow == 'left') {
        return wrap.querySelector('.left-team');
    }
    if (arrow == 'right') {
        return wrap.querySelector('.right-team');
    }
}
function getArrow(target) {
    let goal = target.parentElement;
    if (goal.classList.contains('left-goal')) {
        return 'left';
    }
    if (goal.classList.contains('right-goal')) {
        return 'right';
    }
}
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
    let wrap = matchTeam.parentElement.parentElement;
    if (matchTeam.classList.contains('left-team')) {
        abled(wrap, 'left');
    } else {
        abled(wrap, 'right');
    }
    if (target.classList.contains('red')) {
        matchTeam.classList.add('red');
        teamName.innerHTML = 'RED';
    }
    if (target.classList.contains('blue')) {
        matchTeam.classList.add('blue');
        teamName.innerHTML = 'BLUE';
    }
    if (target.classList.contains('yellow')) {
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
    let resultWrap = document.createElement('div');
    resultWrap.classList.add('match-result-wrap');

    let result = matchResultForm();
    let resultDetail = matchResultDetail();

    resultWrap.appendChild(result);
    resultWrap.appendChild(resultDetail);

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
function createGoal(arrow) {
    let goal = document.createElement('li');
    goal.classList.add('goal');

    let btn = document.createElement('button')
    btn.type = 'button';
    btn.classList.add('addBtn');
    btn.classList.add('addPlayer');
    btn.disabled = 'true';

    if (arrow == 'left') {
        btn.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM232 344V280H168c-13.3 0-24-10.7-24-24s10.7-24 24-24h64V168c0-13.3 10.7-24 24-24s24 10.7 24 24v64h64c13.3 0 24 10.7 24 24s-10.7 24-24 24H280v64c0 13.3-10.7 24-24 24s-24-10.7-24-24z"/></svg>선수추가'
        goal.classList.add('left-goal');
    } else {
        btn.innerHTML = '선수추가<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM232 344V280H168c-13.3 0-24-10.7-24-24s10.7-24 24-24h64V168c0-13.3 10.7-24 24-24s24 10.7 24 24v64h64c13.3 0 24 10.7 24 24s-10.7 24-24 24H280v64c0 13.3-10.7 24-24 24s-24-10.7-24-24z"/></svg>'
        goal.classList.add('right-goal');
    }

    goal.appendChild(btn);
    return goal;
}
function matchResultForm() {
    let matchResult = document.createElement('div');
    matchResult.classList.add('match-result');

    let matchTeamLeft = createMatchTeam('left');
    let middleBox = createMiddleBox();
    let matchTeamRight = createMatchTeam('right');

    matchResult.appendChild(matchTeamLeft);
    matchResult.appendChild(middleBox);
    matchResult.appendChild(matchTeamRight);

    return matchResult;
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

    if (arrow == 'left') {
        teamDetail.classList.add('left-team-detail');
        matchScore.classList.add('left-score');
    } else {
        teamDetail.classList.add('right-team-detail');
        matchScore.classList.add('right-score');
    }
    teamDetail.appendChild(matchScore);
    return teamDetail;
}