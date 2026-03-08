$(document).ready(function() {
    init();
});


var init = function() {
    addEvent();
};

var addEvent = function() {
    $('#buyTab').on('click', function() {
        $("#buyTab").prop("checked", true);
        $("#sellTab").prop("checked", false);
        $("#tradeTab").prop("checked", false);

        $('#buyArea').removeClass('display-none');
        $('#sellArea').addClass('display-none');
        $('#tradeArea').addClass('display-none');
    });

    $('#sellTab').on('click', function() {
        $("#buyTab").prop("checked", false);
        $("#sellTab").prop("checked", true);
        $("#tradeTab").prop("checked", false);

        $('#buyArea').addClass('display-none');
        $('#sellArea').removeClass('display-none');
        $('#tradeArea').addClass('display-none');
    });

    $('#tradeTab').on('click', function() {
        $("#buyTab").prop("checked", false);
        $("#sellTab").prop("checked", false);
        $("#tradeTab").prop("checked", true);

        $('#buyArea').addClass('display-none');
        $('#sellArea').addClass('display-none');
        $('#tradeArea').removeClass('display-none');
    });
};

var BuyCount = {
    accountFront: $('#accountFront').text(),
    accountBack: $('#accountBack').text(),
    code: $('#code').text(),

    init: function() {
        $('#buyCheckBtn').on('click', function() {
            var requestURL = window.$config.API_BASE_URL;


            var successCallback = function(data) {
                
            };

            var errorCallback = function(data) {
                alert('error !!! ');
            }
        });
    }
}