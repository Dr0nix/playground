$(function () {

    var currentPage = 0;
    var pageSize = 10;
    var editor = null;

    var $listView   = $('#listView');
    var $detailView = $('#detailView');
    var $writeView  = $('#writeView');
    var $body       = $('#noticeBody');
    var $paging     = $('#paging');

    var loginUserId = parseInt($('#loginUserId').val()) || 0;
    var isAdmin     = loginUserId === 1;

    // admin이면 글쓰기 버튼 표시
    if (isAdmin) {
        console.log('admin!')
        $('#writeBtn').removeClass('hidden');
    }

    /* ───── 화면 전환 ───── */
    function showView(viewId) {
        $listView.addClass('hidden');
        $detailView.addClass('hidden');
        $writeView.addClass('hidden');
        $(viewId).removeClass('hidden');
    }

    /* ───── 목록 조회 ───── */
    function loadList(page) {
        currentPage = page;

        $.get('/comm/ntc', { page: page, size: pageSize }, function (data) {
            renderList(data.content);
            renderPaging(data);
        });
    }

    function renderList(list) {
        var html = '';

        if (list.length === 0) {
            html = '<tr><td colspan="5" style="text-align:center;padding:40px;color:var(--muted)">등록된 공지사항이 없습니다.</td></tr>';
            $body.html(html);
            return;
        }

        list.forEach(function (ntc) {
            var pin = ntc.pinYn ? '<span class="ntc-pin-badge">고정</span>' : '';
            var date = formatDate(ntc.regDttm);

            html += '<tr data-id="' + ntc.ntcId + '">'
                  +   '<td class="ntc-meta">' + ntc.ntcId + '</td>'
                  +   '<td class="ntc-row-title">' + pin + escapeHtml(ntc.title) + '</td>'
                  +   '<td class="ntc-meta">' + escapeHtml(ntc.regUserNm) + '</td>'
                  +   '<td class="ntc-meta">' + (ntc.viewCount || 0) + '</td>'
                  +   '<td class="ntc-meta">' + date + '</td>'
                  + '</tr>';
        });

        $body.html(html);
    }

    function renderPaging(data) {
        var total = data.totalPages;
        var current = data.number;
        var html = '';

        html += '<button ' + (current <= 0 ? 'disabled' : '') + ' data-page="' + (current - 1) + '">&laquo;</button>';

        var start = Math.max(0, current - 4);
        var end = Math.min(total, start + 9);
        if (end - start < 9) start = Math.max(0, end - 9);

        for (var i = start; i < end; i++) {
            var active = i === current ? ' is-active' : '';
            html += '<button class="' + active + '" data-page="' + i + '">' + (i + 1) + '</button>';
        }

        html += '<button ' + (current >= total - 1 ? 'disabled' : '') + ' data-page="' + (current + 1) + '">&raquo;</button>';

        $paging.html(html);
    }

    /* ───── 상세 조회 ───── */
    function loadDetail(ntcId) {
        $.get('/comm/ntc/' + ntcId, function (data) {
            $('#detailTitle').text(data.title);
            $('#detailWriter').text(data.regUserNm);
            $('#detailDate').text(formatDate(data.regDttm));
            $('#detailViews').text(data.viewCount || 0);
            $('#detailContent').html(data.content || '');

            showView('#detailView');
        });
    }

    /* ───── 작성 ───── */
    function initEditor() {
        if (editor) return;

        editor = new toastui.Editor({
            el: document.querySelector('#editor'),
            height: '400px',
            initialEditType: 'wysiwyg',
            previewStyle: 'vertical',
            placeholder: '내용을 입력하세요...',
            toolbarItems: [
                ['heading', 'bold', 'italic', 'strike'],
                ['hr', 'quote'],
                ['ul', 'ol'],
                ['table', 'image', 'link'],
                ['code', 'codeblock']
            ]
        });
    }

    function openWriteView() {
        initEditor();
        $('#ntcTitleInput').val('');
        $('#ntcPinInput').prop('checked', false);
        editor.reset();
        showView('#writeView');
    }

    function submitNotice() {
        var title = $('#ntcTitleInput').val().trim();
        var content = editor.getHTML();
        var pinYn = $('#ntcPinInput').is(':checked');

        if (!title) {
            alert('제목을 입력하세요.');
            $('#ntcTitleInput').focus();
            return;
        }

        $.ajax({
            url: '/comm/ntc',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ title: title, content: content, pinYn: pinYn }),
            success: function () {
                alert('공지사항이 등록되었습니다.');
                showView('#listView');
                loadList(0);
            },
            error: function (xhr) {
                var msg = xhr.responseJSON ? xhr.responseJSON.error : '등록에 실패했습니다.';
                alert(msg);
            }
        });
    }

    /* ───── 유틸 ───── */
    function formatDate(dateStr) {
        if (!dateStr) return '-';
        var d = new Date(dateStr);
        var y = d.getFullYear();
        var m = ('0' + (d.getMonth() + 1)).slice(-2);
        var day = ('0' + d.getDate()).slice(-2);
        return y + '.' + m + '.' + day;
    }

    function escapeHtml(str) {
        if (!str) return '';
        return str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    }

    /* ───── 이벤트 ───── */

    // 행 클릭 → 상세
    $body.on('click', 'tr[data-id]', function () {
        var ntcId = $(this).data('id');
        loadDetail(ntcId);
    });

    // 페이징
    $paging.on('click', 'button:not(:disabled)', function () {
        var page = $(this).data('page');
        loadList(page);
    });

    // 목록으로 돌아가기
    $('#backToListBtn').on('click', function () {
        showView('#listView');
        loadList(currentPage);
    });

    // 글쓰기 버튼
    $('#writeBtn').on('click', openWriteView);

    // 작성 취소
    $('#cancelWriteBtn').on('click', function () {
        showView('#listView');
    });

    // 등록
    $('#submitWriteBtn').on('click', submitNotice);

    /* ───── 초기 로드 ───── */
    loadList(0);
});
