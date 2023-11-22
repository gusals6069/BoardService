var publicKey = '9XkWxQFHChRq_eGDW';
var serviceKey = 'service_233mko2';
var templateKey = 'template_ff00lgf';

var mailSend = {
    init : () => {
        if(emailjs){ emailjs.init(publicKey); } // init
        // event binding
        if(document.querySelector('#mailForm') != null) {
            document.querySelector('#mailForm input[type="button"]').addEventListener('click', mailSend.send);
        }
    },
    valid : () => {
        var validFlag = true;

        document.querySelectorAll('#mailForm .invalid-feedback').forEach(function(item, index){
            item.style.display = 'none';
        });
        document.querySelectorAll('#mailForm .invalid-check').forEach(function(item, index){
            item.style.border = 'none';

            var validObj = item
            var validMsg = validObj.nextElementSibling;
            if( validObj.value == "" && validMsg != undefined ){
                validMsg.style.display = 'block';
                validObj.style.border  = '1px solid #ff0000';
                validFlag = false;
            }
        });
        return validFlag;
    },
    send : (evt) => {
        if(document.querySelector('#mailForm') == null) return false;

        var validResult = mailSend.valid();
        if( validResult ){
            document.querySelector('#mailForm input[type="button"]').removeEventListener('click', mailSend.send);

            emailjs.sendForm(serviceKey, templateKey, '#mailForm').then((res) => {
                document.querySelector('#mailForm').reset();
                document.querySelector('#mailForm input[type="button"]').addEventListener('click', mailSend.send);

                //modal 추가 필요
            });
        }
    }
}

window.addEventListener('DOMContentLoaded', function(){
    mailSend.init();
});