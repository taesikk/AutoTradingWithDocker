function requestToken() {
    const data = {
      grant_type: $("#grant_type").val(),
      appkey: $("#appkey").val(),
      appsecret: $("#appsecret").val()
    };

    $.ajax({
      url: "/api/token",  // 👉 실제 토큰 발급 API 주소로 변경해줘
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(data),
      success: function(res) {
        $("#access_token").val(res.access_token || "");
        $("#expires_in").val(res.expires_in || "");
      },
      error: function(err) {
        alert("토큰 발급 실패\n" + JSON.stringify(err.responseJSON));
      }
    });
  }