$(function () {

    /* ───── 상태 ───── */
    let balance  = 1000;
    let bet      = 100;
    let gameOver = false;

    /* ───── DOM 캐시 ───── */
    const $overlay        = $('#startOverlay');
    const $board          = $('#gameBoard');
    const $playerCards    = $('#playerCards');
    const $dealerCards    = $('#dealerCards');
    const $playerScore    = $('#playerScore');
    const $dealerScore    = $('#dealerScore');
    const $betDisplay     = $('#betDisplay');
    const $balanceDisplay = $('#balanceDisplay');
    const $deckCount      = $('#deckCount');
    const $message        = $('#gameMessage');
    const $hitBtn         = $('#hitBtn');
    const $standBtn       = $('#standBtn');
    const $retryBtn       = $('#retryBtn');
    const $backBtn        = $('#backBtn');

    /* ───── 렌더링 ───── */
    function renderCard(card) {
        var faceDown = card.faceDown;
        var cls = 'playing-card is-dealt' + (faceDown ? ' is-face-down' : '');
        var imgSrc = '/img/pokerCard/' + card.code + '.png';

        return '<div class="' + cls + '">' +
                   '<div class="playing-card-inner">' +
                       '<div class="playing-card-front">' +
                           '<img src="' + imgSrc + '" alt="' + card.code + '">' +
                       '</div>' +
                       '<div class="playing-card-back">' +
                           '<img src="/img/pokerCard/BACK.png" alt="back">' +
                       '</div>' +
                   '</div>' +
               '</div>';
    }

    function renderHand($container, cards) {
        var html = '';
        cards.forEach(function (card) {
            html += renderCard(card);
        });
        $container.html(html);
    }

    function updateUI(data) {
        renderHand($playerCards, data.playerHand);
        renderHand($dealerCards, data.dealerHand);

        $playerScore.text(data.playerScore);
        $dealerScore.text(data.dealerScore != null ? data.dealerScore : '?');
        $betDisplay.text(bet.toLocaleString());
        $balanceDisplay.text(balance.toLocaleString());
        $deckCount.text('남은 카드: ' + data.deckCount);
    }

    function setMessage(msg) {
        $message.html(msg);
    }

    function toggleActions(playing) {
        if (playing) {
            $hitBtn.removeClass('hidden');
            $standBtn.removeClass('hidden');
            $retryBtn.addClass('hidden');
            $backBtn.addClass('hidden');
        } else {
            $hitBtn.addClass('hidden');
            $standBtn.addClass('hidden');
            $retryBtn.removeClass('hidden');
            $backBtn.removeClass('hidden');
        }
    }

    /* ───── 결과 처리 ───── */
    var RESULT_MSGS = {
        'PLAYER_BLACKJACK': function () { return '🂡 블랙잭! +' + Math.floor(bet * 2.5).toLocaleString(); },
        'PLAYER_WIN':       function () { return '🎉 승리! +' + (bet * 2).toLocaleString(); },
        'DEALER_WIN':       function () { return '😢 패배! -' + bet.toLocaleString(); },
        'DEALER_BLACKJACK': function () { return '💀 딜러 블랙잭! -' + bet.toLocaleString(); },
        'PUSH':             function () { return '🤝 무승부! 베팅 금액 반환'; }
    };

    function settle(result) {
        if (result === 'PLAYER_BLACKJACK') {
            balance += Math.floor(bet * 2.5);
        } else if (result === 'PLAYER_WIN') {
            balance += bet * 2;
        } else if (result === 'PUSH') {
            balance += bet;
        }
        $balanceDisplay.text(balance.toLocaleString());
    }

    function handleResponse(data) {
        updateUI(data);

        if (data.status === 'FINISHED') {
            gameOver = true;
            toggleActions(false);
            settle(data.result);

            var msgFn = RESULT_MSGS[data.result];
            setMessage(msgFn ? msgFn() : data.result);
        }
    }

    /* ───── API 호출 ───── */
    function apiPost(url, body, callback) {
        $.ajax({
            url: url,
            type: 'POST',
            contentType: 'application/json',
            data: body ? JSON.stringify(body) : '{}',
            success: callback,
            error: function (xhr) {
                var msg = xhr.responseJSON ? xhr.responseJSON.error : '서버 오류가 발생했습니다.';
                alert(msg);
            }
        });
    }

    /* ───── 게임 흐름 ───── */
    function startGame() {
        if (bet > balance) {
            alert('잔액이 부족합니다!');
            return;
        }
        balance -= bet;
        gameOver = false;

        apiPost('/game/blackjack/start', { betAmount: bet }, function (data) {
            $overlay.addClass('hidden');
            $board.removeClass('is-hidden');
            toggleActions(true);
            setMessage('히트 또는 스탠드를 선택하세요');

            handleResponse(data);
        });
    }

    function hit() {
        if (gameOver) return;

        apiPost('/game/blackjack/hit', null, function (data) {
            handleResponse(data);

            if (data.status !== 'FINISHED') {
                setMessage('히트 또는 스탠드를 선택하세요');
            }
        });
    }

    function stand() {
        if (gameOver) return;

        apiPost('/game/blackjack/stand', null, function (data) {
            handleResponse(data);
        });
    }

    /* ───── 이벤트 바인딩 ───── */

    // 칩 선택
    $('#chipList').on('click', '.bet-chip', function () {
        $('.bet-chip').removeClass('is-selected');
        $(this).addClass('is-selected');
        bet = parseInt($(this).data('bet'));
    });

    // 시작
    $('#startBtn').on('click', startGame);

    // 히트 / 스탠드
    $hitBtn.on('click', hit);
    $standBtn.on('click', stand);

    // 다시하기 (같은 베팅으로 바로 시작)
    $retryBtn.on('click', function () {
        if (balance <= 0) {
            balance = 1000;
            alert('잔액이 부족하여 1,000으로 초기화되었습니다.');
        }
        startGame();
    });

    // 돌아가기 (시작 화면으로)
    $backBtn.on('click', function () {
        if (balance <= 0) {
            balance = 1000;
            alert('잔액이 부족하여 1,000으로 초기화되었습니다.');
        }
        $overlay.removeClass('hidden');
        $board.addClass('is-hidden');
    });
});