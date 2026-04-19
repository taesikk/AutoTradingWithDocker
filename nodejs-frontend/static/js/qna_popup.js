'use strict'

$("#saveBtn").click(function () {

  const writer = $("#writer").val();
  const title = $("#title").val();
  const content = $("#content").val();

  if (!writer || !title || !content) {
    alert("모든 값을 입력해주세요.");
    return;
  }

  // 👉 부모창으로 데이터 전달
  window.opener.addPost({
    writer: writer,
    title: title,
    content: content
  });

  window.close();
});