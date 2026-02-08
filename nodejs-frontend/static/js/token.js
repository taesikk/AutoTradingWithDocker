'use strict'

$(document).ready(function() {
    init();
});

var init = function() {
    addEvent();
    copyOnClick($('#accessToken'));
    copyOnClick($('#expiresIn'));
};


var addEvent = function() {
    $('#tokenBtn').on('click', function() {
      const data = {
        grantType: $("#grant_type").val(),
        appkey: $("#appkey").val(),
        appsecret: $("#appsecret").val()
      };

      var requestURL = window.$config.API_BASE_URL + '/token';

      var successCallback = function(data) {
        const accessToken = data.data.accessToken;
        const expiredDate = data.data.expiredDate;

        $('#accessToken').val(accessToken);
        $('#expiresIn').val(expiredDate);

        localStorage.setItem('access_token', accessToken);
        localStorage.setItem('expired_date', expiredDate);
      };

      var errorCallback = function(data) {
        alert('error !!! ');
      };

      jsonAjax(requestURL, 'POST', true, data, successCallback, errorCallback);
    });
};

var copyOnClick = function(selector) {
  $(selector).on('click', function () {
    navigator.clipboard.writeText(this.value)
      .then(() => {
        alert('복사되었습니다!');
      });
  });
};