$(function () {

    var currentPage = 0;
    var pageSize = 10;
    var editor = null;
    var editingNtcId = null; // null이면 작성, 값이 있으면 수정
    var currentDetailId = null;

    var $listView   = $('#listView');
    var $detailView = $('#detailView');
    var $writeView  = $('#writeView');
    var $body       = $('#noticeBody');
    var $paging     = $('#paging');

    var loginUserId = parseInt($('#loginUserId').val()) || 0;
    var isAdmin     = loginUserId === 1;

    // admin이면 글쓰기/수정/삭제 버튼 표시
    if (isAdmin) {
        $('#writeBtn').removeClass('hidden');
        $('#editBtn').removeClass('hidden');
        $('#deleteBtn').removeClass('hidden');
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
            currentDetailId = ntcId;

            $('#detailTitle').text(data.title);
            $('#detailWriter').text(data.regUserNm);
            $('#detailDate').text(formatDate(data.regDttm));
            $('#detailViews').text(data.viewCount || 0);
            $('#detailContent').html(data.content || '');

            // 수정 시 사용할 데이터 저장
            $detailView.data('ntc', data);

            showView('#detailView');
        });
    }

    /* ───── 작성 / 수정 ───── */
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
        editingNtcId = null;
        $('#writeViewTitle').text('공지사항 작성');
        $('#submitWriteBtn').text('등록');
        $('#ntcTitleInput').val('');
        $('#ntcPinInput').prop('checked', false);
        editor.reset();
        showView('#writeView');
    }

    function openEditView() {
        initEditor();
        var data = $detailView.data('ntc');
        editingNtcId = data.ntcId;

        $('#writeViewTitle').text('공지사항 수정');
        $('#submitWriteBtn').text('수정');
        $('#ntcTitleInput').val(data.title);
        $('#ntcPinInput').prop('checked', data.pinYn);
        editor.setHTML(data.content || '');
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

        var payload = JSON.stringify({ title: title, content: content, pinYn: pinYn });

        if (editingNtcId) {
            // 수정
            $.ajax({
                url: '/comm/ntc/' + editingNtcId,
                type: 'PUT',
                contentType: 'application/json',
                data: payload,
                success: function () {
                    alert('수정되었습니다.');
                    loadDetail(editingNtcId);
                },
                error: function (xhr) {
                    var msg = xhr.responseJSON ? xhr.responseJSON.error : '수정에 실패했습니다.';
                    alert(msg);
                }
            });
        } else {
            // 작성
            $.ajax({
                url: '/comm/ntc',
                type: 'POST',
                contentType: 'application/json',
                data: payload,
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
    }

    /* ───── 삭제 ───── */
    function deleteNotice() {
        if (!confirm('정말 삭제하시겠습니까?')) return;

        $.ajax({
            url: '/comm/ntc/' + currentDetailId,
            type: 'DELETE',
            success: function () {
                alert('삭제되었습니다.');
                showView('#listView');
                loadList(currentPage);
            },
            error: function (xhr) {
                var msg = xhr.responseJSON ? xhr.responseJSON.error : '삭제에 실패했습니다.';
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
        loadDetail($(this).data('id'));
    });

    // 페이징
    $paging.on('click', 'button:not(:disabled)', function () {
        loadList($(this).data('page'));
    });

    // 목록으로 돌아가기
    $('#backToListBtn').on('click', function () {
        showView('#listView');
        loadList(currentPage);
    });

    // 글쓰기
    $('#writeBtn').on('click', openWriteView);

    // 수정
    $('#editBtn').on('click', openEditView);

    // 삭제
    $('#deleteBtn').on('click', deleteNotice);

    // 작성/수정 취소
    $('#cancelWriteBtn').on('click', function () {
        if (editingNtcId) {
            loadDetail(editingNtcId);
        } else {
            showView('#listView');
        }
    });

    // 등록/수정 제출
    $('#submitWriteBtn').on('click', submitNotice);

    /* ───── 초기 로드 ───── */
    loadList(0);
});
