window.addEventListener('load', () => {

    let confirmBtnList = document.querySelectorAll('.confirmBtn');

    confirmBtnList.forEach(btn => {
        btn.addEventListener('click', () => {
            let myMatchBox = btn.parentElement.parentElement.parentElement;
            let myMatchId = getMatchId(myMatchBox);
            let teamList = getTeamList(myMatchBox);
            if (myMatchId == null || teamList == null) {
                alert('저장에 문제가 생겼습니다. 관리자에게 문의해주세요.');
                return;
            }
            let resultJson = getJson(myMatchId, teamList);
            console.log(resultJson);
            fetchPost('/match/team/confirm', resultJson, result);

        })
    })

})

function result(map) {
    alert(map.message);
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

function getJson(myMatchId, teamList) {
    let json = {matchId : myMatchId};

    let childrens = teamList.children;
    
    let teamArray = [];
    for (let i=0;i<childrens.length;i++) {
        let teamChildren = childrens[i].children;
        let color = getColor(teamChildren);
        let players = getPlayers(teamChildren);
        let team = {teamColor : color, player : players};
        teamArray.push(team);
    }
    json = {...json, team : teamArray};

    return json;
}

function getColor(team) {
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