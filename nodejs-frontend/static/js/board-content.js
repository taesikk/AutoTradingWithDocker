'use strict'

$(document).ready(function () {
    init();
});

var init = function() {
    addEvent();
    loadPost();
};

var addEvent = function() {
    $("#btnBackList").click(function () {
        location.href = 'board.html';
    });

    $("#commentSubmitBtn").click(function () {
        var comment = $("#commentInput").val();
        var params = new URLSearchParams(window.location.search);
        var id = params.get('id');

        if (!comment || String(comment).trim() === "") {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

        var requestURL = window.$config.API_BASE_URL + "/qna/comment";
        var data = {
            idx: id,
            title: "",
            content: "",
            creator: "",
            comment: String(comment).trim()
        };

        var successCallback = function () {
            alert("댓글 작성 완료");
            //location.reload();
            loadPost();
        };

        var errorCallback = function () {
            alert("댓글 작성 실패");
        };

        jsonAjax(requestURL, "POST", true, data, successCallback, errorCallback);
    });
};

var longToDate = function(long) {
    var date = new Date(long);
    return date.toLocaleDateString();
};

var formatPostDate = function (post) {
    if (post.createDate != null && post.createDate !== '') {
        return String(post.createDate);
    }
    if (post.updateDate != null && post.updateDate !== '') {
        return String(post.updateDate);
    }
    if (post.createdAt != null && post.createdAt !== '') {
        var n = Number(post.createdAt);
        if (!isNaN(n) && String(post.createdAt).length >= 10) {
            return longToDate(post.createdAt);
        }
        return String(post.createdAt);
    }
    return '';
};

var renderComments = function (post) {
    var $list = $('#commentList');
    $list.empty();

    var comment = post.comment;

    // if (Array.isArray(comment)) {
    if (comment.length === 0) {
        $list.append($('<p class="comment-empty">').text('댓글이 없습니다.'));
        return;
    }
    var $item = $('<div class="comment-item">');
    $item.append($('<div class="comment-text">').text(String(comment)));
    $list.append($item);
    // comment.forEach(function (c) {
    //     var $item = $('<div class="comment-item">');
    //     if (c != null && typeof c === 'object') {
    //         var author = c.creator != null ? c.creator : (c.writer != null ? c.writer : '');
    //         var at = c.createDate != null ? c.createDate : (c.createdAt != null ? c.createdAt : '');
    //         var metaParts = [];
    //         if (author !== '') {
    //             metaParts.push(author);
    //         }
    //         if (at !== '') {
    //             metaParts.push(String(at));
    //         }
    //         if (metaParts.length > 0) {
    //             $item.append($('<div class="comment-meta">').text(metaParts.join(' · ')));
    //         }
    //         var text = c.content != null ? c.content : (c.text != null ? c.text : '');
    //         $item.append($('<div class="comment-text">').text(String(text)));
    //     } else {
    //         $item.append($('<div class="comment-text">').text(String(c)));
    //     }
    //     $list.append($item);
    // });
    return;
    // }

    if (comment != null && comment !== '') {
        if (typeof comment === 'number' || (typeof comment === 'string' && /^\d+$/.test(String(comment).trim()))) {
            var n = Number(comment);
            if (n === 0) {
                $list.append($('<p class="comment-empty">').text('댓글이 없습니다.'));
            } else {
                $list.append($('<p class="comment-empty">').text('댓글 ' + n + '개'));
            }
            return;
        }
        $list.append($('<div class="comment-item">').append(
            $('<div class="comment-text">').text(String(comment))
        ));
        return;
    }

    $list.append($('<p class="comment-empty">').text('댓글이 없습니다.'));
};

var renderPost = function (post) {
    $('#detailTitle').text(post.title != null ? String(post.title) : '');

    var creator = post.creator != null ? String(post.creator) : '';
    var dateStr = formatPostDate(post);
    var metaParts = [];
    if (creator !== '') {
        metaParts.push('작성자 ' + creator);
    }
    if (dateStr !== '') {
        metaParts.push(dateStr);
    }
    $('#detailMeta').text(metaParts.join(' · '));

    $('#detailBody').text(post.content != null ? String(post.content) : '');

    renderComments(post);

    $('#detailWrap').show();
    $('#detailError').hide();
};

var showLoadError = function (message) {
    $('#detailWrap').hide();
    $('#detailError').text(message).show();
};

var loadPost = function () {
    var params = new URLSearchParams(window.location.search);
    var idxParam = params.get('idx');

    var raw = sessionStorage.getItem('board_view_post');
    if (!raw) {
        showLoadError('게시글 정보를 찾을 수 없습니다. 목록에서 다시 선택해 주세요.');
        return;
    }

    var post;
    try {
        post = JSON.parse(raw);
    } catch (e) {
        showLoadError('게시글 데이터를 읽을 수 없습니다.');
        return;
    }

    if (idxParam != null && idxParam !== '' && String(post.idx) !== String(idxParam)) {
        showLoadError('요청한 글과 저장된 정보가 일치하지 않습니다.');
        return;
    }

    renderPost(post);
};
