function ajax(url, method, async, data, successCallback, errorCallback) {
    $.ajax({
      url: url,
      type: method,
      contentType: "application/json",
      data: data,
      async: async !== undefined ? async : true,
      success: successCallback,
      error: errorCallback
    })      
}

function jsonAjax(url, method, async, data, successCallback, errorCallback) {
    $.ajax({
      url: url,
      type: method,
      contentType: "application/json",
      data: JSON.stringify(data),
      async: async !== undefined ? async : true,
      success: successCallback,
      error: errorCallback
    })      
}