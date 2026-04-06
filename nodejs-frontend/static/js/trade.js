$(document).ready(function() {
    init();
});


var init = function() {
    addEvent();
    BuyCheck.init();
    SellCheck.init();
    BuyStock.init();
};

var addEvent = function() {
    $('#buyTab').on('click', function() {
        $("#buyTab").prop("checked", true);
        $("#sellTab").prop("checked", false);
        $("#tradeBuyTab").prop("checked", false);
        $("#tradeSellTab").prop("checked", false);

        $('#buyArea').removeClass('display-none');
        $('#sellArea').addClass('display-none');
        $('#tradeBuyArea').addClass('display-none');
        $('#tradeSellArea').addClass('display-none');
    });

    $('#sellTab').on('click', function() {
        $("#buyTab").prop("checked", false);
        $("#sellTab").prop("checked", true);
        $("#tradeBuyTab").prop("checked", false);
        $("#tradeSellTab").prop("checked", false);

        $('#buyArea').addClass('display-none');
        $('#sellArea').removeClass('display-none');
        $('#tradeBuyArea').addClass('display-none');
        $('#tradeSellArea').addClass('display-none');
    });

    $('#tradeBuyTab').on('click', function() {
        $("#buyTab").prop("checked", false);
        $("#sellTab").prop("checked", false);
        $("#tradeBuyTab").prop("checked", true);
        $("#tradeSellTab").prop("checked", false);

        $('#buyArea').addClass('display-none');
        $('#sellArea').addClass('display-none');
        $('#tradeBuyArea').removeClass('display-none');
        $('#tradeSellArea').addClass('display-none');
    });

    $('#tradeSellTab').on('click', function() {
        $("#buyTab").prop("checked", false);
        $("#sellTab").prop("checked", false);
        $("#tradeTab").prop("checked", false);
        $("#tradeSellTab").prop("checked", true);

        $('#buyArea').addClass('display-none');
        $('#sellArea').addClass('display-none');
        $('#tradeBuyArea').addClass('display-none');
        $('#tradeSellArea').removeClass('display-none');
    });
};

var BuyCheck = {
    accountFront: $('#buyCheckAccountFront'),
    accountBack: $('#buyCheckAccountBack'),
    code: $('#buyCheckCode'),

    init: function() {
        const _this = this;
        $('#buyCheckBtn').on('click', function() {
            const requestURL = window.$config.API_BASE_URL + '/buyCheck';
            const data = {
                accessToken: localStorage.getItem('access_token'),
	            appkey: localStorage.getItem('appkey'),
	            appsecret: localStorage.getItem('appsecret'),
	            accountFront: _this.accountFront.val(),
	            accountBack: _this.accountBack.val(),
	            code: _this.code.val()
            };


            var successCallback = function(data) {
                const buyCount = data.data;
                $('#buyAvailableQty').val(buyCount);
            };

            var errorCallback = function(data) {
                alert(data);
            };

            jsonAjax(requestURL, 'POST', true, data, successCallback, errorCallback);
        });
    }
};

var SellCheck = {
    accountFront: $('#sellCheckAccountFront'),
    accountBack: $('#sellCheckAccountBack'),
    code: $('#sellCheckCode'),

    init: function() {
        const _this = this;
        $('#sellCheckBtn').on('click', function() {
            const requestURL = window.$config.API_BASE_URL + '/sellCheck';
            const data = {
                accessToken: localStorage.getItem('access_token'),
	            appkey: localStorage.getItem('appkey'),
	            appsecret: localStorage.getItem('appsecret'),
	            accountFront: _this.accountFront.val(),
	            accountBack: _this.accountBack.val(),
	            code: _this.code.val()
            };


            var successCallback = function(data) {
                const sellCount = data.data;
                $('#sellAvailableQty').val(sellCount);
            };

            var errorCallback = function(data) {
                alert(data);
            };

            jsonAjax(requestURL, 'POST', true, data, successCallback, errorCallback);
        });
    }
};

var BuyStock = {
    accountFront: $('#buyAccountFront'),
    accountBack: $('#buyAccountBack'),
    code: $('#buyCode'),
    amount: $('#buyAmount'),
    
    init: function() {
        const _this = this;
        $('#orderBtn').on('click', function() {
            const requestURL = window.$config.API_BASE_URL + '/buyStock/' + _this.amount.val() ;
            const data = {
                accessToken: localStorage.getItem('access_token'),
	            appkey: localStorage.getItem('appkey'),
	            appsecret: localStorage.getItem('appsecret'),
	            accountFront: _this.accountFront.val(),
	            accountBack: _this.accountBack.val(),
	            code: _this.code.val()
            };

            var successCallback = function(data) {
                const orderResult = data.data;
                if (orderResult === 'Y' || orderResult === 'y') {
                    $('#orderResult').val('주문에 성공하였습니다.');
                } else {
                    $('#orderResult').val('주문에 실패하였습니다.');
                }
                
            };

            var errorCallback = function(data) {
                alert(data);
            };

            jsonAjax(requestURL, 'POST', true, data, successCallback, errorCallback);

        });
    }
};

var SellStock = {
    accountFront: $('#sellAccountFront'),
    accountBack: $('#sellAccountBack'),
    code: $('#sellCode'),
    amount: $('#sellAmount'),
    init: function() {
        const _this = this;
        $('#orderBtn').on('click', function() {
            const requestURL = window.$config.API_BASE_URL + '/sellStock/' + _this.amount.val() ;
            const data = {
                accessToken: localStorage.getItem('access_token'),
	            appkey: localStorage.getItem('appkey'),
	            appsecret: localStorage.getItem('appsecret'),
	            accountFront: _this.accountFront.val(),
	            accountBack: _this.accountBack.val(),
	            code: _this.code.val()
            };

            var successCallback = function(data) {
                const orderResult = data.data;
                if (orderResult === 'Y' || orderResult === 'y') {
                    $('#sellOrderResult').val('주문에 성공하였습니다.');
                } else {
                    $('#sellOrderResult').val('주문에 실패하였습니다.');
                }
                
            };

            var errorCallback = function(data) {
                alert(data);
            };

            jsonAjax(requestURL, 'POST', true, data, successCallback, errorCallback);

        });
    }
}