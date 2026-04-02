$(function () {

    var currentPage = 0;
    var pageSize = 10;

    var $listView   = $('#listView');
    var $detailView = $('#detailView');
    var $body       = $('#noticeBody');
    var $paging     = $('#paging');

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
            $('#detailContent').text(data.content || '');

            $listView.addClass('hidden');
            $detailView.removeClass('hidden');
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
        $detailView.addClass('hidden');
        $listView.removeClass('hidden');
        loadList(currentPage);
    });

    /* ───── 초기 로드 ───── */
    loadList(0);
});
