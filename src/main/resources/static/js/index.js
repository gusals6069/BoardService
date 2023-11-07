var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            modal.show('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            console.log(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'PUT',
            url: '/api/posts/'+$('#id').val(),
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            modal.show('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            console.log(JSON.stringify(error));
        });
    },
    delete : function () {
        $.ajax({
            type: 'DELETE',
            url: '/api/posts/'+$('#id').val(),
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            modal.show('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            console.log(JSON.stringify(error));
        });
    }
};

var modalObject;
var modal = {
    init : function () {
        modalObject = bootstrap.Modal.getOrCreateInstance('#alertModal');
    },
    show : function (msg) {
        if(modalObject == null) return;

        document.querySelector('.modal-body').textContent = msg; // 로드마다 치환
        modalObject.show();
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
});

