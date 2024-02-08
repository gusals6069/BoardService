var post = {
    init : function(){
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
                location.href = '/posts/list#board';
            });
        }

        if(document.querySelector('#btn-view-page') != null) {
            document.querySelector('#btn-view-page').addEventListener('click', function(evt){
                if(this.dataset.target != null){
                    location.href = '/posts/view/' + this.dataset.target;
                }
            });
        }

        if(document.querySelector('#btn-save-page') != null) {
            document.querySelector('#btn-save-page').addEventListener('click', function(evt){
                location.href = '/posts/save';
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
    list : function(pageNo){
        var queryParam = "";
        //var searchType = document.getElementById('searchForm').searchType.value;

        //if( )

        if(text.isNotEmpty(pageNo)){
            location.href = '/posts/list?page=' + pageNo + '#board';
        }else{
            location.href = '/posts/list?page=1#board';
        }
    },
    view : function(postId){
        if(text.isNotEmpty(postId)){
            location.href = '/posts/view/' + postId;
        }
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
            modal.alert('success', '글이 등록되었습니다.',function(){
                 window.location.href = '/posts/list';
            });
        }).fail(function (error) {
            if(error.responseJSON.type != undefined){
                if(error.responseJSON.type == 'valid'){
                    post.valid(error);
                }else{
                    modal.alert('error','오류가 발생했습니다.',null);
                }
            }else{
                console.log(error);
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
            modal.alert('success', '글이 수정되었습니다.',function(){
                 window.location.href = '/posts/list';
            });
        }).fail(function (error) {
            if(error.responseJSON.type == 'valid'){
                post.valid(error);
            }else{
                modal.alert('error','오류가 발생했습니다.',null);
            }
        });
    },
    delete : function () {
        modal.confirm('warning','글을 삭제하시겠습니까?',function(){
            $.ajax({
                type: 'DELETE',
                url: '/api/posts/'+document.querySelector('#id').value,
                dataType: 'json',
                contentType:'application/json; charset=utf-8'
            }).done(function() {
                modal.alert('success', '글이 삭제되었습니다.',function(){
                     window.location.href = '/posts/list';
                });
            }).fail(function (error) {
                modal.alert('error','오류가 발생했습니다.',null);
            });
        });
    },
    valid : function(res) {
        document.querySelectorAll('#postForm .invalid-feedback').forEach(function(item, index){
            item.style.display = 'none';
        });

        document.querySelectorAll('#postForm .invalid-check').forEach(function(item, index){
            item.style.border = 'none';
        });

        if(res.responseJSON.data.length > 0){
            console.log(res.responseJSON.data);
            res.responseJSON.data.forEach(function(item, index){
                var validObj = document.querySelector('#'+item.fieldId);
                var validMsg = validObj.nextElementSibling;

                if( validMsg != undefined ){
                    validObj.style.border  = '1px solid #ff0000';
                    validMsg.style.display = 'block';
                    validMsg.lastElementChild.textContent = item.message;
                }
            });

            //document.querySelector('#'+res.responseJSON.data[0].fieldId).focus();
            return;
        }
    }
}

window.addEventListener('DOMContentLoaded', function(){
    post.init();
});