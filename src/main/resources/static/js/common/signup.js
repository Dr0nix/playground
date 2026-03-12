let DEFAULT_URL = '/signup'

$(document).ready(() => {
    setEventListner();
});

function gotoLoginPage() {
    console.log('go back!!')
    location.href = '/login-page';
}

function signup() {
    var userNm = $('#su-name').val();
    var userNickname = $('#su-nickname').val();
    var userEmail = $('#su-email').val();
    var userPw = $('#su-password').val();

    $.ajax({
        url: DEFAULT_URL,
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify({ userNm, userNickname, userEmail, userPw }),
        success: () => {
            alert('성공적으로 가입되었습니다');
            location.href = '/';
        },
        error: (xhr) => {
          alert('가입에 실패하였습니다.');
        }
    });
}

function validateEveryThing() {
    var isValidName = validateName();
    var isValidNickname = validateNickname();
    var isValidEmail = validateEmail();
    var isValidPw = validatePassword();

    if(isValidName && isValidNickname && isValidEmail && isValidPw) {
        $('#signupSubmit').attr('disabled', false);
    }
    else {
        $('#signupSubmit').attr('disabled', true);
    }
}

function validateName() {
    var result = false;

    var name = $('#su-name').val();

    // null이 아니고 길이가 2 이상이라면
    if(name !== null && name.length > 1) {
        result = true;
    }

    return result;
}

function validateNickname() {
    var result = false;

    var nickname = $('#su-nickname').val();

    if(nickname !== null && nickname.trim().length > 1) {
        result = true;
    }

    return result;
}

function validateEmail() {
    var result = false;

    var email = $('#su-email').val();
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 이메일 패턴 정규식

    // null이 아니고 이메일 패턴이 맞다면
    if(email !== null && emailPattern.test(email)) {
        result = true;
    }

    return result;
}

function validatePassword() {
    var result = false;

    var pw1 = $('#su-password').val();
    var pw2 = $('#su-password2').val();

    var pwPattern = /^(?=.*[A-Z])(?=.*\d).{8,}$/; // 대문자, 숫자 포함 8자리 이상

    // null이 아니고 pw패턴에 맞으며 입력과 확인 값이 같으면
    if(pw1 !== null && pwPattern.test(pw1) && pw1 === pw2) {
        result = true;
    }

    return result;
}

function setEventListner() {
    $('#back').click(gotoLoginPage);

    $('#signupSubmit').click(signup);

    $('#su-name').on('change', validateEveryThing);
    $('#su-nickname').on('change', validateEveryThing);
    $('#su-email').on('change', validateEveryThing);
    $('#su-password').on('change', validateEveryThing);
    $('#su-password2').on('change', validateEveryThing);
}
