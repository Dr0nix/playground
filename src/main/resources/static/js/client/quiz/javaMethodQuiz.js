let DEFAULT_URL = '/quiz/java-method';
let quizList = [];
let curIdx = 0;
let score = 0;

$(document).ready(() => {
    setEventListener();
});

function startJavaMethodQuiz(difficulty, roundCount) {
    $.ajax({
        url: DEFAULT_URL + '/start',
        type: 'GET',
        data: {difficulty, roundCount},
        success: function (data) {
            console.log(data)
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

    if(!quiz) {
        endQuiz();
        return;
    }

    $('#qNo').text(curIdx + 1);
    $('#qText').text(quiz.questionText);
    $('#answer').val('').focus();
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
    showResult(isCorrect, quiz.answerText, quiz.explanation);
}

function showResult(isCorrect, answerText, explanation) {
    $('#resultCard').removeClass('hidden');
    $('#resultActions').removeClass('hidden');

    $('#resultTitle')
        .text(isCorrect ? '정답입니다!' : '오답입니다')
        .removeClass('correct wrong')
        .addClass(isCorrect ? 'correct' : 'wrong');

    if (isCorrect) {
        $('#resultAnswer').addClass('hidden');
    } else {
        $('#answerText').text(answerText);
        $('#resultAnswer').removeClass('hidden');
    }

    $('#resultExplanation').text(explanation ?? '');

    $('#answerArea').addClass('hidden');
    $('#toolbarArea').addClass('hidden');
}

function hideResult() {
    $('#resultCard').addClass('hidden');
    $('#resultActions').addClass('hidden');
    $('#resultAnswer').addClass('hidden');
    $('#resultExplanation').text('');

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
        let difficulty = $('#difficulty').val();

        startJavaMethodQuiz(difficulty, roundCount);
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
}