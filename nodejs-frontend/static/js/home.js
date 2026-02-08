'use strict'

$(document).ready(function() {
    init();
});

var init = function() {
    setTokenInfo();
};

var setTokenInfo = function() {
  var accessToken = localStorage.getItem('access_token') != null ? localStorage.getItem('access_token') : '';
  var expiredDate = localStorage.getItem('expired_date') != null ? localStorage.getItem('expired_date') : '';

  if (accessToken == '') {
    $("#tokenStatus").removeClass('valid').addClass('expired');
    $("#tokenStatus").text("● 만료");
  } else {
    $('#accessToken').text(accessToken);
    $('#tokenExpire').text(expiredDate);
  }
};