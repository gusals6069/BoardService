
var modalObject;

var modal = {
    init : function(){
        modalObject = Swal.mixin({
            customClass: {
                popup           : "modal_frame",
                icon            : "modal_icon",
                title           : "modal_title",
                confirmButton   : "modal_button",
                denyButton      : "modal_button",
                cancelButton    : "modal_button",
                htmlContainer   : "modal_text"
            },
            buttonsStyling: false
        });
    },
    alert : function(type, message, callback) {
        modalObject.fire({
            title: 'ALERT',
            text: message,
            icon: type, // success, error, warning, info, question
            showCancelButton: false,
            showDenyButton: false,
            confirmButtonText: 'Confirm'
        }).then((result) => {
            if (result.isConfirmed) {
                if(callback != null && typeof callback === 'function'){
                     setTimeout(callback, 10);
                }
            }
        });
    },
    confirm : function(type, message, callback) {
        modalObject.fire({
            title: 'CONFIRM',
            text: message,
            icon: type, // success, error, warning, info, question
            showCancelButton: false,
            showDenyButton: true,
            confirmButtonText: 'Confirm',
            denyButtonText: "Cancel"
        }).then((result) => {
            // 확인을 눌렀을 경우에만 처리
            if (result.isConfirmed) {
                if(callback != null && typeof callback === 'function'){
                     setTimeout(callback, 10);
                }
            }
        });
    }
}

window.addEventListener('DOMContentLoaded', function(){
    modal.init();
});