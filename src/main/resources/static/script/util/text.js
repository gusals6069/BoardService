var text = {
    init : function(){
        var _this = this;
    },
    trim : function(str){
        return str.replace(/(^\s*)|(\s*$)/gi, "");
    },
    isEmpty : function(str){
        if(str == null || str == undefined){
            return true;
        }else{
            if(typeof str == "string"){
                if(text.trim(str) == ""){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    },
    isNotEmpty : function(str){
        return !text.isEmpty(str);
    },
    setDate : function(date){
        if(text.isEmpty(date) || date.length != 8 || !isValidDate(date)){ return null; }

        var year  = Number(date.substring(0,4));
        var month = Number(date.substring(4,6)) - 1;
        var day   = Number(date.substring(6,8));

        return new Date(year,month,day);
    },
    getDate : function(type){
        if(text.isEmpty(type)){ type = "1"; }

        var date = new Date();
        var yyyy = date.getFullYear();
        var MM = (date.getMonth()+1) < 10 ? "0" + (date.getMonth()+1) : (date.getMonth()+1);
        var dd = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hh = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var mm = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var ss = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        // type 1 : yyyyMMddhhmmss
        // type 2 : yyyyMMdd
        // type 3 : yyyyMM
        // type 4 : yyyy

        if(type == "1"){
            return yyyy + MM + dd + hh + mm + ss;
        }else if(type == "2"){
            return yyyy + MM + dd;
        }else if(type == "3"){
            return yyyy + MM;
        }else if(type == "4"){
            return yyyy;
        }
    },
    isValidDate : function(date){
        var dateString = date.replace(/\s/gi, "");
        if( dateString == "" || dateString.length != 8 ){
            return false;
        }

        //유효한(존재하는) 일(日)인지 체크
        var year  = dateString.substring(0,4);
        var month = dateString.substring(4,6);
        var day   = dateString.substring(6,8);

        var mm = parseInt(month,10);
        var dd = parseInt(day,10);

        //월 체크
        if(! (mm >= 1 && mm <= 12)) {
           return false;
        }

        var end = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            end[1] = 29;
        }

        return (dd >= 1 && dd <= end[mm-1]);
    },
    setCookie : function(name, value, expires) {
        if(expires == null || expires == ""){
            expires = new Date();
            expires.setTime(expires.getTime() + 1000 * 60 * 60 * 24 * 30);
        }

        document.cookie = name + "=" + escape (value) + "; path=/; expires=" + expires.toGMTString();
    },
    getCookie : function(name) {
        var search = Name + "="
        if (document.cookie.length > 0) {
            offset = document.cookie.indexOf(search)
            if (offset != -1) { // 쿠키가 존재하면
                offset += search.length
                // set index of beginning of value
                end = document.cookie.indexOf(";", offset)

                if (end == -1)
                    end = document.cookie.length
                return unescape(document.cookie.substring(offset, end))
            }
        }
        return "";
    }
}

window.addEventListener('DOMContentLoaded', function(){
    text.init();
});