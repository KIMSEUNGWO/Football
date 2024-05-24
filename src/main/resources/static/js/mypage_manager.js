window.addEventListener('load', () => {

    let teamConfirmBtn = document.querySelectorAll('.teamConfirm');

    teamConfirmBtn.forEach(btn => {
        btn.addEventListener('click', () => {
            let myMatchBox = btn.parentElement.parentElement.parentElement;
            let myMatchId = getMatchId(myMatchBox);
            let teamList = getTeamList(myMatchBox);
            if (myMatchId == null || isNaN(Number(myMatchId)) || teamList == null) {
                alert('오류가 생겼습니다. 관리자에게 문의해주세요.');
                return;
            }
            let resultJson = getJson(teamList);
            let confirm = window.confirm('팀을 확정하시겠습니까?');
            if (confirm) {
                fetchPost('/manager/team/' + myMatchId, resultJson, result);
            }

        })
    })

    let matchEnd = document.querySelectorAll('.matchEnd');

    matchEnd.forEach(btn => {
        btn.addEventListener('click', () => {
            let myMatchBox = btn.parentElement.parentElement.parentElement;
            let myMatchId = getMatchId(myMatchBox);
            if (myMatchId == null || isNaN(Number(myMatchId))) {
                alert('오류가 생겼습니다. 관리자에게 문의해주세요.');
                return;
            }
            // let isEarly = checkTime(myMatchId);
            // if (isEarly) {
            // }
            let confirm = window.confirm('경기를 종료하시겠습니까?');
            if (confirm) {
                fetchPost('/manager/end/' + myMatchId, null, result);
            }

        })
    })

    let scoreBtn = document.querySelectorAll('.scoreConfirm');
    scoreBtn.forEach(el => {
        el.addEventListener('click', () => {
            let resultWrap = el.parentElement.parentElement.querySelectorAll('.match-result-wrap');
            let data = el.parentElement.parentElement.parentElement.getAttribute('data-is-id');

            let array = [];
            for (let i=0;i<resultWrap.length;i++) {
                let form = getForm(resultWrap[i]);
                if (form == null) {
                    alert('팀을 모두 설정해주세요.');
                    return;
                }
                array.push(form);
            }
            let resultJson = {playList : array};
        
            let confirm = window.confirm('기록을 확정하시겠습니까?');
            if (confirm) {
                fetchPost('/manager/record/' + data, resultJson, result);
            }

        })
    })

})

function getForm(parent) {
    
    let leftTeam = parent.querySelector('.left-team .team-name').textContent;
    let rightTeam = parent.querySelector('.right-team .team-name').textContent;

    if ((leftTeam != 'RED' && leftTeam != 'BLUE' && leftTeam != 'YELLOW') || (rightTeam != 'RED' && rightTeam != 'BLUE' && rightTeam != 'YELLOW')) {
        return null;
    }

    let leftScore = parent.querySelector('.left-score').textContent;
    let rightScore = parent.querySelector('.right-score').textContent;

    let array = [];
    array.push({team : leftTeam, score : Number(leftScore)});
    array.push({team : rightTeam, score : Number(rightScore)});

    return array;
}

function checkTime(matchId) {
    return fetchGet('/match/timeCheck/' + matchId, isEarly);
}

function isEarly(map) {
    return map;
}

function result(map) {
    alert(map.message);
    if (map.result == 'NotLogin') {
        this.location.href = '/login';
    }
    this.location.reload();
}

function getMatchId(myMatchBox) {
    if (myMatchBox == null) return null;
    let matchBox = myMatchBox.children;
    for (let i=0;i<matchBox.length;i++) {
        if (matchBox[i].classList.contains('topInfo')) {
            let topInfo = matchBox[i].children;
            for (let j=0;j<topInfo.length;j++) {
                if (topInfo[j].classList.contains('matchId')) {
                    return topInfo[j].textContent;
                }
            }
        }
    }
    return null;
}
function getTeamList(myMatchBox) {
    if (myMatchBox == null) return null;
    let matchBox = myMatchBox.children;
    for (let i=0;i<matchBox.length;i++) {
        if (matchBox[i].classList.contains('matchTeamConfirm')) {
            let matchTeamConfirm = matchBox[i].children;
            for (let j=0;j<matchTeamConfirm.length;j++) {
                if (matchTeamConfirm[j].classList.contains('teamList')) {
                    return matchTeamConfirm[j];
                }
            }
        }
    }
    return null;

}

function getJson(teamList) {
    let childrens = teamList.children;
    
    let teamArray = [];
    for (let i=0;i<childrens.length;i++) {
        let teamChildren = childrens[i].children;
        let color = getColors(teamChildren);
        let players = getPlayers(teamChildren);
        let team = {teamColor : color, player : players};
        teamArray.push(team);
    }
    let json = {team : teamArray};

    return json;
}

function getColors(team) {
    for (let i=0;i<team.length;i++) {
        if (team[i].classList.contains('teamColor')) {
            return team[i].textContent;
        }
    }
    return null;
}
function getPlayers(team) {
    for (let i=0;i<team.length;i++) {
        if (team[i].classList.contains('players')) {
            return getPlayerArray(team[i]);
        }
    }
    return null;
}

function getPlayerArray(players) {
    let playerList =  players.children;
    let array = [];
    for (let i=0;i<playerList.length;i++) {
        array.push(Number(playerList[i].value));
    }
    return array;
}