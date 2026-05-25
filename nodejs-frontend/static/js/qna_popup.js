'use strict'

var init = function () {
  addEvent();
};

var resetAndClosePopup = function () {
  $(".form-box").find("input, textarea").val("");
  window.close();
};

var addEvent = function () {
  $("#saveBtn").click(function () {
    const writer = $("#writer").val();
    const title = $("#title").val();
    const content = $("#content").val();

    if (!writer || !title || !content) {
      alert("모든 값을 입력해주세요.");
      return;
    }

    var requestURL = window.$config.API_BASE_URL + "/qna/write";
    var data = {
      creator: writer,
      title: title,
      content: content,
      comment: ""
    };

    var successCallback = function () {
      alert("게시글이 등록되었습니다.");
      resetAndClosePopup();
    };

    var errorCallback = function () {
      alert("게시글 등록에 실패하였습니다.");
    };

    jsonAjax(requestURL, "POST", true, data, successCallback, errorCallback);
  });

  $("#closePopupBtn").click(function () {
    resetAndClosePopup();
  });
};

$(document).ready(function () {
  init();
});