var publicKey = '9XkWxQFHChRq_eGDW';
var serviceKey = 'service_233mko2';
var templateKey = 'template_ff00lgf';

var mailSend = {
    init : () => {
        if(emailjs){ emailjs.init(publicKey); } // init
        // event binding
        if(document.querySelector('#mailForm') != null) {
            document.querySelector('#mailForm input[type="button"].send').addEventListener('click', mailSend.send);
            document.querySelector('#mailForm input[type="button"].reset').addEventListener('click', mailSend.reset);
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
            if( validObj.value == '' && validMsg != undefined ){
                validMsg.style.display = 'block';
                validObj.style.border  = '1px solid #ff0000';
                validFlag = false;
            }

            if( validObj.value != '' && validObj.classList.contains('mail-format') && validMsg != undefined){
                var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
                if(!email_regex.test(validObj.value)){
                    validMsg.style.display = 'block';
                    validObj.style.border  = '1px solid #ff0000';
                    validFlag = false;
                }
            }
        });
        return validFlag;
    },
    send : (evt) => {
        if(document.querySelector('#mailForm') == null) return false;

        var validResult = mailSend.valid();
        if( validResult ){
            var target = document.querySelector('#mailForm button.send');
            var sendSubmit = ()=> {
                document.querySelector('#mailForm input[type="button"].send').disabled = true;
                document.querySelector('#mailForm input[type="button"].send').removeEventListener('click', mailSend.send);

                emailjs.sendForm(serviceKey, templateKey, '#mailForm').then((res) => {
                    document.querySelector('#mailForm').reset();
                    document.querySelector('#mailForm input[type="button"].send').disabled = false;
                    document.querySelector('#mailForm input[type="button"].send').addEventListener('click', mailSend.send);

                    // alert custom
                    modal.alert('success', '메일이 전송되었습니다.', null);
                });
            }
            modal.confirm('question', '메일을 전송하시겠습니까?', sendSubmit);
        }
    },
    reset : () => {
        document.querySelectorAll('#mailForm .invalid-feedback').forEach(function(item, index){
            item.style.display = 'none';
        });
        document.querySelectorAll('#mailForm .invalid-check').forEach(function(item, index){
            item.style.border = 'none';
        });
        document.querySelector('#mailForm').reset();
    }
}

window.addEventListener('DOMContentLoaded', function(){
    mailSend.init();
});