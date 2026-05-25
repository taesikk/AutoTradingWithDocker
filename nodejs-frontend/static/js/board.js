'use strict'

var postData = [];
var skip = 0;
var limit = 10;

$(document).ready(function () {
    init();
});


var init = function() {
    addEvent();
    getPostList();
    pagination.init();

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

        $row.find('[clonekey="idx"]').text(post.id);
        $row.find('[clonekey="title"]').text(post.title);
        $row.find('[clonekey="creator"]').text(post.creator);

        var createDate = longToDate(post.createDate);
        $row.find('[clonekey="createDate"]').text(createDate);

        $row.addClass('board-data-row');
        $row.on('click', function () {
            sessionStorage.setItem('board_view_post', JSON.stringify(post));
            location.href = 'board-content.html?id=' + encodeURIComponent(post.id);
        });

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
  var now = new Date().getTime();
  var requestURL = window.$config.API_BASE_URL + '/qna/list';
  var data = {
    keyword: '',
    sort: 'id',
    skip : skip,
    limit : limit,
    startDate : 0,
    endDate : now
  };

  var successCallback = function(data) {
      postData = data.data || [];
      pagination.currentPage = 1;
      refreshBoard();
  };

  var errorCallback = function(data) {
      alert(data);
  };

  jsonAjax(requestURL, 'POST', true, data, successCallback, errorCallback);
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
