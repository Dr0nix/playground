let DEFAULT_URL = '/quiz/four-idioms';
let quizList = [];
let curIdx = 0;
let score = 0;
let openIndexes = [];
let difficulty = 'HARD';

$(document).ready(() => {
    setEventListener();
});

function startFourIdiomsQuiz(roundCount) {
    difficulty = $('#difficulty').val();

    $.ajax({
        url: DEFAULT_URL + '/start',
        type: 'GET',
        data: {roundCount},
        success: data => {
            quizList = data;
            curIdx = 0;
            score = 0;

            $('#score').text(score);
            $('#qTotal').text(quizList.length);

            showQuiz();
        },
        error: (e) => {
            alert('문제를 불러오는데 오류가 발생했습니다 : ' + e.message);
        }
    });

    $('#introStage').addClass('hidden');
    $('#quizStage').removeClass('hidden');
}

function showQuiz() {
    const quiz = quizList[curIdx];
    openIndexes = [];

    if(!quiz) {
        endQuiz();
        return;
    }

    $('#hintBtn').prop('disabled', false);

    // 초기 공개 위치 하나 선택
    const firstIdx = pickOpenIndex();
    if(firstIdx !== -1) {
        openIndexes.push(firstIdx);
    }

    if(difficulty === 'HARD') {
        $('#hintBtn').prop('disabled', true);
        renderHintText(quiz.answerText, false);
    }
    else if(difficulty === 'NORMAL') {
        renderHintText(quiz.answerText, false);
    }
    else { // EASY
        renderHintText(quiz.answerText, true);
    }

    $('#qNo').text(curIdx + 1);
    $('#qText').text(quiz.questionText);
    $('#answer').val('').focus();
}

function pickOpenIndex() {
    const candidates = [0,1,2,3].filter(i => !openIndexes.includes(i));

    if(candidates.length === 0) return -1;

    return candidates[Math.floor(Math.random() * candidates.length)];
}

function getHint(quiz) {
    const newIdx = pickOpenIndex();

    if(newIdx === -1) return;

    openIndexes.push(newIdx);

    if(difficulty === 'EASY') {
        renderHintText(quiz.answerText, true);
    } else {
        renderHintText(quiz.answerText, false);
    }
}

function renderHintText(answer, useChosung=false) {

    const chosung = getChosung(answer);

    let hintText = '';

    for(let i=0;i<4;i++) {

        if(openIndexes.includes(i)) {
            hintText += answer[i] + ' ';
        }
        else if(useChosung) {
            hintText += chosung[i] + ' ';
        }
        else {
            hintText += '_ ';
        }
    }

    $('#hintText').text(hintText.trim());
}

function getChosung(text) {

    const CHO = [
        'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ',
        'ㄹ','ㅁ','ㅂ','ㅃ','ㅅ',
        'ㅆ','ㅇ','ㅈ','ㅉ','ㅊ',
        'ㅋ','ㅌ','ㅍ','ㅎ'
    ];

    let result = '';

    for(const ch of text) {

        const code = ch.charCodeAt(0);

        if(code >= 0xac00 && code <= 0xd7a3) {

            const choIndex = Math.floor((code - 0xac00) / 588);

            result += CHO[choIndex];

        } else {

            result += ch;
        }
    }

    return result;
}

function submitAnswer() {

    const quiz = quizList[curIdx];
    const inputAnswer = $('#answer').val().trim();

    let isCorrect = false;

    if(!quiz) return;

    if(inputAnswer === quiz.answerText) {

        score++;
        isCorrect = true;

        $('#score').text(score);
    }

    curIdx++;

    showResult(isCorrect, quiz.answerText, quiz.hanjaText);
}

function showResult(isCorrect, answerText, hanja) {

    $('#resultCard').removeClass('hidden');
    $('#resultActions').removeClass('hidden');

    $('#resultTitle')
        .text(isCorrect ? '정답입니다!' : '오답입니다')
        .removeClass('correct wrong')
        .addClass(isCorrect ? 'correct' : 'wrong');

    if (isCorrect) {

        $('#resultAnswer').addClass('hidden');

    } else {

        $('#answerText').text(answerText + '(' + hanja + ')');

        $('#resultAnswer').removeClass('hidden');
    }

    $('#answerArea').addClass('hidden');
    $('#toolbarArea').addClass('hidden');
}

function hideResult() {

    $('#resultCard').addClass('hidden');
    $('#resultActions').addClass('hidden');
    $('#resultAnswer').addClass('hidden');

    $('#answerArea').removeClass('hidden');
    $('#toolbarArea').removeClass('hidden');

    $('#nextBtn').removeClass('hidden');
    $('#restartBtn').addClass('hidden');

    showQuiz();
}

function endQuiz() {

    $('#resultCard').removeClass('hidden');
    $('#resultActions').removeClass('hidden');

    $('#resultTitle')
        .text('퀴즈 종료!')
        .removeClass('correct wrong');

    $('#resultAnswer')
        .removeClass('hidden')
        .html(`최종 점수 : <strong>${score}</strong> / ${quizList.length}`);

    $('#resultExplanation').text(
        `모든 문제를 완료했습니다.\n처음으로 돌아가 다시 시작할 수 있습니다.`
    );

    $('#answerArea').addClass('hidden');
    $('#toolbarArea').addClass('hidden');

    $('#nextBtn').addClass('hidden');
    $('#restartBtn').removeClass('hidden');
}

function setEventListener() {

    $('#startBtn').click(() => {

        let roundCount = $('#roundCount').val();

        startFourIdiomsQuiz(roundCount);
    });

    $('#submit').click(submitAnswer);

    $('#nextBtn').click(hideResult);

    $('#restartBtn').click(() => {

        document.cookie = "skipPageLogOnce=Y; path=/";

        window.location.reload();
    });

    $('#skipBtn').click(() => {

        curIdx++;

        showQuiz();
    });

    $('#hintBtn').click(() => {

        let quiz = quizList[curIdx];

        getHint(quiz);

        $('#hintBtn').prop('disabled', true);
    });

    $('#answer').on('keydown', e => {

        if (e.key === 'Enter') {
            e.preventDefault();
            $('#submit').click();
        }
    });

    $('#resultCard').on('keydown', e => {

        if (e.key === 'Enter') {
            e.preventDefault();
            $('#nextBtn').click();
        }
    });
}