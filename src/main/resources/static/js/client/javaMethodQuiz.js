let DEFAULT_URL = '/quiz/java-method';

$(document).ready(() => {
    setEventListener();
});

function startJavaMethodQuiz(difficulty, roundCount) {
    console.log('diff : ' + difficulty + '/ rc : ' + roundCount)
    let quizList = [];

    $.ajax({
        url: DEFAULT_URL + '/start',
        type: 'GET',
        data: {difficulty, roundCount},
        success: data => {
            console.log(data);
        },
    });

    // 화면 호출
    $('#introStage').addClass('hidden');
    $('#quizStage').removeClass('hidden');


}



function setEventListener() {
    $('#startBtn').click(() => {
        let roundCount = $('#roundCount').val();
        let difficulty = $('#difficulty').val();

        startJavaMethodQuiz(roundCount, difficulty);
    });
}