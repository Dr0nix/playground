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

    if(!quiz) return;

    if(inputAnswer === quiz.answerText) {
        score++;
        $('#score').text(score);
    }

    curIdx++;
    showQuiz();
}

function endQuiz() {
    alert(`퀴즈 종료! 점수: ${score}/${quizList.length}`);
}

function setEventListener() {
    $('#startBtn').click(() => {
        let roundCount = $('#roundCount').val();
        let difficulty = $('#difficulty').val();

        startJavaMethodQuiz(difficulty, roundCount);
    });

    $('#submit').click(submitAnswer);
}