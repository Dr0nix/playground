let DEFAULT_URL = '/quiz/four-idioms';
let quizList = [];
let curIdx = 0;
let score = 0;
let openIdx = -1;

$(document).ready(() => {
    setEventListener();
});

function startFourIdiomsQuiz(roundCount) {
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

    // 화면 호출
    $('#introStage').addClass('hidden');
    $('#quizStage').removeClass('hidden');
}

function showQuiz() {
    const quiz = quizList[curIdx];
    openIdx = -1;

    getHint(quiz); // 초기에 열어줄 위치는 중복일 수 없음

    if(!quiz) {
        endQuiz();
        return;
    }

    $('#qNo').text(curIdx + 1);
    $('#qText').text(quiz.questionText);
    $('#answer').val('').focus();
    $('#hintBtn').prop('disabled', false);
}

function getHint(quiz) {
    const answer = quiz.answerText;
    let randomIdx = -1;

    while (true) {
        randomIdx = Math.floor(Math.random() * 4);

        if (randomIdx !== openIdx) {
            break;
        }
    }


    let hintText = '';

    for (let i = 0; i < 4; i++) {
        if (i === openIdx || i === randomIdx) {
            hintText += answer[i] + ' ';
        } else {
            hintText += '_ ';
        }
    }
    openIdx = randomIdx;

    $('#hintText').text(hintText);
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
}