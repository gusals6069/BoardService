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
    },
    view : function (id) {
        window.location.href = '/posts/update/' + id;
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
            modal.show('글이 등록되었습니다.', function(){
                 window.location.href = '/';
            });
        }).fail(function (error) {
            //console.log(JSON.stringify(error));
            //console.log(JSON.parse(JSON.stringify(error)));
            var res = JSON.parse(JSON.stringify(error));
            var err = res.responseText;
            var errParse = JSON.parse(err);

            //console.log(res);
            //console.log(err);
            //console.log(errParse);
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
            modal.show('글이 수정되었습니다.', function(){
                window.location.href = '/';
            });
        }).fail(function (error) {
            console.log(JSON.stringify(error));
        });
    },
    delete : function () {
        $.ajax({
            type: 'DELETE',
            url: '/api/posts/'+document.querySelector('#id').value,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            modal.show('글이 삭제되었습니다.', function(){
                window.location.href = '/';
            });
        }).fail(function (error) {
            console.log(JSON.stringify(error));
        });
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
    }
};

var modalObject;
var modalCloseBtn;

var modal = {
    init : function () {
        modalObject = bootstrap.Modal.getOrCreateInstance('#alertModal');
        modalCloseBtn = document.querySelectorAll('.btn-modal-close');
    },
    show : function (msg, callback) {
        if(modalObject == null) return;

        document.querySelector('.modal-body').textContent = msg; // 로드마다 치환
        modalObject.show();

        if(callback != null){
            modalCloseBtn.forEach(function(item, idx){
                item.addEventListener('click', function(evt){
                    evt.preventDefault();
                    callback();
                });
            });
            modalObject.hide();
        }
    },
    hide : function () {
        if(modalObject == null) return;

        document.querySelector('.modal-body').textContent = ''; // 로드마다 치환
        modalObject.hide();
    }
}

window.addEventListener('DOMContentLoaded', function(){
    main.init();
    modal.init();
    main.resize();
});

window.addEventListener('resize', function(){
   main.resize();
});
