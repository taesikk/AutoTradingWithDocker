'use strict'

var postData = [];
var skip = 0;
var limit = 10;

$(document).ready(function () {
    init();
});


var init = function() {
    //getPostList();
    addEvent();
    pagination.init();

    // test data
    postData.push({
      idx: 1,
      title: 'test',
      creator: 'test',
      content: 'test 입니다.',
      createdAt: 1718745600000,
    },{
      idx: 2,
      title: 'test2',
      creator: 'test2',
      content: 'test2 입니다.',
      createdAt: 1718745600000,
    },{
      idx: 3,
      title: 'test3',
      creator: 'test3',
      content: 'test3 입니다.',
      createdAt: 1718745600000,
    },
    {
      idx: 4,
      title: 'test4',
      creator: 'test4',
      content: 'test4 입니다.',
      createdAt: 1718745600000,
    },
    {
      idx: 5,
      title: 'test5',
      creator: 'test5',
      content: 'test5 입니다.',
      createdAt: 1718745600000,
    },
    {
      idx: 6,
      title: 'test6',
      creator: 'test6',
      content: 'test6 입니다.',
      createdAt: 1718745600000,
    },
    {
      idx: 7,
      title: 'test7',
      creator: 'test7',
      content: 'test7 입니다.',
      createdAt: 1718745600000,
    },
    {
      idx: 8,
      title: 'test8',
      creator: 'test8',
      content: 'test8 입니다.',
      createdAt: 1718745600000,
    },
    {
      idx: 9,
      title: 'test9',
      creator: 'test9',
      content: 'test9 입니다.',
      createdAt: 1718745600000,
    },
    {
      idx: 10,
      title: 'test10',
      creator: 'test10',
      content: 'test10 입니다.',
      createdAt: 1718745600000,
    },
    {
      idx: 11,
      title: 'test11',
      creator: 'test11',
      content: 'test11 입니다.',
      createdAt: 1718745600000,
    });
    pagination.currentPage = 1;
    refreshBoard();
};

var addEvent = function() {
    $("#writeBtn").click(function () {
        window.open(
            "qna_popup.html",
            "writePopup",
            "width=500,height=600,scrollbars=yes"
        );
    });
};

var longToDate = function(long) {
    var date = new Date(long);
    return date.toLocaleDateString();
};

var renderTable = function() {
    var $tbody = $('#boardBody');
    var $sample = $tbody.find('[clonesample="tableSample"]');

    $tbody.find('tr').not('[clonesample="tableSample"]').remove();

    var pageSize = pagination.pageSize;
    var cur = pagination.currentPage;
    var start = (cur - 1) * pageSize;
    var items = (postData || []).slice(start, start + pageSize);

    items.forEach(function (post) {
        var $row = $sample.clone(false);
        $row.removeAttr('clonesample');
        $row.removeAttr('style').show();

        $row.find('[clonekey="idx"]').text(post.idx);
        $row.find('[clonekey="title"]').text(post.title);
        $row.find('[clonekey="creator"]').text(post.creator);

        var createdAt = longToDate(post.createdAt);
        $row.find('[clonekey="createDate"]').text(createdAt);

        $row.addClass('board-data-row');
        $row.on('click', function () {
            sessionStorage.setItem('board_view_post', JSON.stringify(post));
            location.href = 'board-content.html?idx=' + encodeURIComponent(post.idx);
        });

        // $row.find('[clonekey]').each(function () {
        //     var $cell = $(this);
        //     var key = $cell.attr('clonekey');
        //     $cell.text(cellTextByClonekey(post, key));
        //     if (key === 'title') {
        //         var content = post.content != null ? String(post.content) : '';
        //         var preview = content.length > 200 ? content.slice(0, 200) + '…' : content;
        //         $cell.attr('title', preview);
        //     }
        // });

        $tbody.append($row);
    });
};

var renderPagination = function () {
    var $pg = $('#pagination');
    $pg.empty();

    var total = (postData || []).length;
    var pageSize = pagination.pageSize;
    var totalPage = total === 0 ? 1 : Math.ceil(total / pageSize);
    if (pagination.currentPage > totalPage) {
        pagination.currentPage = totalPage;
    }
    if (pagination.currentPage < 1) {
        pagination.currentPage = 1;
    }

    var cur = pagination.currentPage;

    var $prev = $('<span class="page-btn" id="pageBtnPrev">이전</span>');
    $prev.data('page', cur > 1 ? cur - 1 : null);
    if (cur <= 1) {
        $prev.css({ opacity: 0.4, cursor: 'default' });
    }
    $pg.append($prev);

    for (var p = 1; p <= totalPage; p++) {
        var $btn = $('<span class="page-btn"></span>');
        $btn.attr('id', 'pageBtn' + p);
        $btn.text(p);
        $btn.data('page', p);
        if (p === cur) {
            $btn.css({ fontWeight: 'bold', color: '#333' });
        }
        $pg.append($btn);
    }

    var $next = $('<span class="page-btn" id="pageBtnNext">다음</span>');
    $next.data('page', cur < totalPage ? cur + 1 : null);
    if (cur >= totalPage) {
        $next.css({ opacity: 0.4, cursor: 'default' });
    }
    $pg.append($next);
};

var refreshBoard = function () {
    renderTable();
    renderPagination();
};

var getPostList = function() {
    var requestURL = window.$config.API_BASE_URL + '/post/list';
    var data = {
        accessToken: localStorage.getItem('access_token'),
        appkey: localStorage.getItem('appkey'),
        appsecret: localStorage.getItem('appsecret'),
    };

    var successCallback = function(data) {
        postData = data.data || [];
        postData.push({
          idx: 1,
          title: 'test',
          creator: 'test',
          createdAt: '2026-04-19',
        },{
          idx: 2,
          title: 'test2',
          creator: 'test2',
          createdAt: '2026-04-19',
        },{
          idx: 3,
          title: 'test3',
          creator: 'test3',
          createdAt: '2026-04-19',
        });
        pagination.currentPage = 1;
        refreshBoard();
    };

    var errorCallback = function(data) {
        alert(data);
    };

    jsonAjax(requestURL, 'GET', true, data, successCallback, errorCallback);
};

var pagination = {
  total: 0,
  totalPage: 0,
  currentPage: 1,
  pageSize: 10,
  startPage: 0,
  endPage: 0,
  prevPage: 0,
  nextPage: 0,
  pageList: [],
  pageBtnList: [],
  init: function() {
    var self = this;
    $('#pagination').on('click', '.page-btn', function () {
      var page = $(this).data('page');
      if (page == null) {
        return;
      }
      self.currentPage = page;
      refreshBoard();
    });
  }
};
