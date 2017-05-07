var utils = {};

utils.now = function(format) {

    var date = new Date();

    var hour = date.getHours();
    hour = (hour < 10 ? "0" : "") + hour;

    var min  = date.getMinutes();
    min = (min < 10 ? "0" : "") + min;

    var sec  = date.getSeconds();
    sec = (sec < 10 ? "0" : "") + sec;

    var year = date.getFullYear();

    var month = date.getMonth() + 1;
    month = (month < 10 ? "0" : "") + month;

    var day  = date.getDate();
    day = (day < 10 ? "0" : "") + day;

    format.replace('hh', hour);
    format.replace('mm', min);
    format.replace('ss', sec);
    format.replace('dd', day);
    format.replace('MM', month);
    format.replace('yyyy', year);

    return format;
}

module.exports = utils;