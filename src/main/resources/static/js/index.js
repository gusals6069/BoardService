var main = {
    init : function () {
        var _this = this;

        if(document.querySelector('#btn-save') != null) {
            document.querySelector('#btn-save').addEventListener('click', function(evt){
                evt.preventDefault();
                _this.save();
            });
        }

        if(document.querySelector('#btn-update') != null) {
            document.querySelector('#btn-update').addEventListener('click', function(evt){
                evt.preventDefault();
                _this.update();
            });
        }

        if(document.querySelector('#btn-delete') != null) {
            document.querySelector('#btn-delete').addEventListener('click', function(evt){
                evt.preventDefault();
                _this.delete();
            });
        }

        if(document.querySelector('#btn-list-page') != null) {
            document.querySelector('#btn-list-page').addEventListener('click', function(evt){
                location.href = '/'; // 미구현
            });
        }

        if(document.querySelector('#btn-view-page') != null) {
            document.querySelector('#btn-view-page').addEventListener('click', function(evt){
                if(this.dataset.target != null){
                    location.href = '/posts/view/' + this.dataset.target;
                }
            });
        }

        if(document.querySelector('#btn-update-page') != null) {
            document.querySelector('#btn-update-page').addEventListener('click', function(evt){
                if(this.dataset.target != null){
                    location.href = '/posts/update/' + this.dataset.target;
                }
            });
        }
    },
    view : function (id) {
        location.href = '/posts/view/' + id;
    },
    save : function () {
        var data = {
            title: document.querySelector('#title').value,
            author: document.querySelector('#author').value,
            content: document.querySelector('#content').value,
            category: document.querySelector('#category').value
        };

        $.ajax({
            type: 'POST',
            url: '/api/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            modal.modalOpen('alert', '글이 등록되었습니다.',function(){
                 window.location.href = '/';
            });
        }).fail(function (error) {
            if(error.responseJSON.type == 'valid'){
                main.valid(error);
            }else{
                modal.modalOpen('alert','오류가 발생했습니다.',null);
            }
        });
    },
    update : function () {
        var data = {
            title: document.querySelector('#title').value,
            content: document.querySelector('#content').value,
            category: document.querySelector('#category').value
        };

        $.ajax({
            type: 'PUT',
            url: '/api/posts/'+document.querySelector('#id').value,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            modal.modalOpen('alert','글이 수정되었습니다.',function(){
                window.location.href = '/';
            });
        }).fail(function (error) {
            if(error.responseJSON.type == 'valid'){
                main.valid(error);
            }else{
                modal.modalOpen('alert','오류가 발생했습니다.',null);
            }
        });
    },
    delete : function () {
        modal.modalOpen('confirm','글을 삭제하시겠습니까?',function(){
            $.ajax({
                type: 'DELETE',
                url: '/api/posts/'+document.querySelector('#id').value,
                dataType: 'json',
                contentType:'application/json; charset=utf-8'
            }).done(function() {
                modal.modalOpen('alert','글이 삭제되었습니다.',function(){
                    window.location.href = '/';
                });
            }).fail(function (error) {
                console.log(error);
                if(error.responseJSON.type == 'valid'){
                    main.valid(error);
                }else{
                    modal.modalOpen('alert','오류가 발생했습니다.',null);
                }
            });
        });
    },
    valid : function(res) {
        document.querySelectorAll('.form-error').forEach(function(item, index){
            item.style.display = 'none';
        });

        document.querySelectorAll('.form-control').forEach(function(item, index){
            item.style.border = '1px solid #ced4da';
        });

        if(res.responseJSON.data.length > 0){
            res.responseJSON.data.forEach(function(item, index){
                var validObj = document.querySelector('#'+item.fieldId);
                var validMsg = validObj.nextElementSibling;

                if( validMsg != undefined ){
                    validObj.style.border  = '1px solid #ff0000';
                    validMsg.style.display = 'block';
                    validMsg.textContent = item.message;
                }
            });
        }
    },
    resize : function () {
        if(document.querySelector('.table-lg') != null && document.querySelector('.table-sm') != null) {
            if(window.innerWidth > 540){
                document.querySelector('.table-lg').style.display = 'inline-table';
                document.querySelector('.table-sm').style.display = 'none';
            }else{
                document.querySelector('.table-lg').style.display = 'none';
                document.querySelector('.table-sm').style.display = 'inline-table';
            }
        }
    },
    login : function () {
        location.href = "/userLogin";
    },
    logout : function () {
        modal.modalOpen('alert', '로그아웃 되셨습니다.',function(){
            window.location.href = '/logout';
        });
    }
};

window.addEventListener('DOMContentLoaded', function(){
    main.init();
    main.resize();
});

window.addEventListener('resize', function(){
   main.resize();
});