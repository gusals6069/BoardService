var post = {
    init : function(){
        var _this = this;

        if(document.querySelector('#btn-search') != null) {
            document.querySelector('#btn-search').addEventListener('click', function(evt){
                evt.preventDefault();
                _this.list(1); // 검색을 했을 경우 무조건 첫페이지로
            });
        }

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
                var params = post.query();

                if( text.isNotEmpty(params) ){
                    location.href = '/posts/list' + params;
                }
            });
        }

        if(document.querySelector('#btn-view-page') != null) {
            document.querySelector('#btn-view-page').addEventListener('click', function(evt){
                if(this.dataset.target != null){
                    var params = post.query();

                    if( text.isNotEmpty(params) ){
                        location.href = '/posts/view/' + this.dataset.target + params;
                    }
                }
            });
        }

        if(document.querySelector('#btn-save-page') != null) {
            document.querySelector('#btn-save-page').addEventListener('click', function(evt){
                var params = post.query();

                if( text.isNotEmpty(params) ){
                    location.href = '/posts/save' + params;
                }
            });
        }

        if(document.querySelector('#btn-update-page') != null) {
            document.querySelector('#btn-update-page').addEventListener('click', function(evt){
                if(this.dataset.target != null){
                    var params = post.query();

                    if( text.isNotEmpty(params) ){
                        location.href = '/posts/update/' + this.dataset.target + params;
                    }
                }
            });
        }
    },
    list : function(pageNo){
        var params = post.query(pageNo);

        if( text.isNotEmpty(params) ){
            location.href = '/posts/list' + params;
        }
    },
    view : function(postId){
        var params = post.query();

        if( text.isNotEmpty(postId) ){
            location.href = '/posts/view/' + postId + params;
        }
    },
    save : function () {
        var formData = {
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
            data: JSON.stringify(formData)
        }).done(function() {
            modal.alert('success', '글이 등록되었습니다.',function(){
                post.list(1); // 새글을 작성했을 경우는 첫페이지로
            });
        }).fail(function (error) {
            if(error.responseJSON.type != undefined){
                if(error.responseJSON.type == 'valid'){
                    post.valid(error);
                }else if(error.responseJSON.type == 'login'){
                    modal.alert('error','로그인 해주시기 바랍니다.', function(){
                        location.href = "/user/login";
                    });
                }else{
                    modal.alert('error','오류가 발생했습니다.',null);
                }
            }else{
                console.log(error);
            }
        });
    },
    update : function () {
        var formData = {
            title: document.querySelector('#title').value,
            content: document.querySelector('#content').value,
            category: document.querySelector('#category').value
        };

        $.ajax({
            type: 'PUT',
            url: '/api/posts/' + document.querySelector('#id').value,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(formData)
        }).done(function() {
            modal.alert('success', '글이 수정되었습니다.',function(){
                post.list();
            });
        }).fail(function (error) {
            if(error.responseJSON.type == 'valid'){
                post.valid(error);
            }else if(error.responseJSON.type == 'login'){
                modal.alert('error','로그인 해주시기 바랍니다.', function(){
                    location.href = "/user/login";
                });
            }else{
                modal.alert('error','오류가 발생했습니다.',null);
            }
        });
    },
    delete : function () {
        modal.confirm('warning','글을 삭제하시겠습니까?',function(){
            $.ajax({
                type: 'DELETE',
                url: '/api/posts/' + document.querySelector('#id').value,
                dataType: 'json',
                contentType:'application/json; charset=utf-8'
            }).done(function() {
                modal.alert('success', '글이 삭제되었습니다.',function(){
                     post.list();
                });
            }).fail(function (error) {
                if(error.responseJSON.type == 'login'){
                    modal.alert('error','로그인 해주시기 바랍니다.', function(){
                        location.href = "/user/login";
                    });
                }else{
                    modal.alert('error','오류가 발생했습니다.',null);
                }
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
            //console.log(res.responseJSON.data);
            res.responseJSON.data.forEach(function(item, index){
                var validObj = document.querySelector('#'+item.fieldId);
                var validMsg = validObj.nextElementSibling;

                if( validMsg != undefined ){
                    validObj.style.border  = '1px solid #ff0000';
                    validMsg.style.display = 'block';
                    validMsg.lastElementChild.textContent = item.message;
                }
            });

            return;
        }
    },
    query : function(pageNo){
        var queryString = "";

        if(text.isNotEmpty(pageNo)){
            queryString += '?pageNo=' + pageNo;
        }else{
            var param = document.getElementById('pageNo').value;
            if( text.isNotEmpty(param) ){
                queryString += '?pageNo=' + param;
            }else{
                queryString += '?pageNo=1';
            }
        }

        var searchType    = document.getElementById('searchType').value;
        var searchKeyword = document.getElementById('searchKeyword').value;

        if( text.isNotEmpty(searchType) && text.isNotEmpty(searchKeyword)){
            queryString += "&searchType=" + searchType + "&searchKeyword=" + searchKeyword;
        }else{
            if(text.isNotEmpty(searchKeyword)){
                modal.alert('warning', '분류를 선택해주시기 바랍니다.', function(){
                    document.getElementById('searchType').selectedIndex = 0;
                    document.getElementById('searchType').focus();
                });
                return null;
            }
        }

        return queryString;
    }
}

window.addEventListener('DOMContentLoaded', function(){
    post.init();
});