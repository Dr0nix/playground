$(document).ready(() => {
    setEventListener();
});

function setEventListener() {
    $('#startBtn').click(() => {
        console.log('good')
        $('#introStage').addClass('hidden');
        $('#quizStage').removeClass('hidden');
    })
}