var modalObject;
var modalObjectEvent = {close: null, confirm: null};

var modal = {
    modalChk : function() {
        var isDisplay = false;
        document.querySelectorAll(".modal").forEach(function(item, index){
            if(item.classList.contains("show")){ isDisplay = true; }
        });
        return isDisplay;
    },
    modalOpen : function(type, message, callback) {
        if(!modal.modalChk()){
            modalObject = bootstrap.Modal.getOrCreateInstance('#' + type + 'Modal');

            var btnHide = modalObject._element.querySelector(".btn-modal-close");
            if( btnHide != null ){
                if(modalObjectEvent.close != null){
                    btnHide.removeEventListener('click', modalObjectEvent.close);
                    modalObjectEvent.close = null;
                }
                modalObjectEvent.close = function(){
                    modal.modalHide();
                    if(type == 'alert'){
                        if(callback != null && typeof callback === 'function'){
                            setTimeout(callback, 500);
                        }
                    }
                }
                btnHide.addEventListener('click', modalObjectEvent.close);
            }
            var btnExec = modalObject._element.querySelector(".btn-modal-confirm");
            if( btnExec != null ){
                if(modalObjectEvent.confirm != null){
                    btnExec.removeEventListener('click', modalObjectEvent.confirm);
                    modalObjectEvent.confirm = null;
                }
                modalObjectEvent.confirm = function(){
                    if(type == 'confirm'){
                        modal.modalHide();
                        if(callback != null && typeof callback === 'function'){
                            setTimeout(callback, 500);
                        }
                    }
                }
                btnExec.addEventListener('click', modalObjectEvent.confirm);
            }
            modalObject._element.querySelector(".modal-body").textContent = message;
            modalObject.show();
        }else{
            console.log("already activated");
        }
    },
    modalHide : function() {
        if(modalObject == null) return;
        modalObject._element.querySelector(".modal-body").textContent = null;
        modalObject.hide();
    }
}