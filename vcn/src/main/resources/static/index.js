function fnAddParam(target) {
    var $target = $(target);

    var addTemplate = '<tr>' +
                        '<td></td>' +
                        '<td><input type="text" name="action" value=""/></td>' +
                        '<td><input type="text" name="action" value=""/></td>' +
                        '<td>required</td>' +
                        '<td></td>';

    $target.after(addTemplate);
}

function fnCVVRequest() {

}

function fnVcnRequest() {
    if ($(".vcn_section").is(":visible") == true) {
        $(".vcn_section").hide();
        return;
    }

    $(".vcn_section").show();
}

function fnJsonSend(formName) {

    var queryString = $("#"+formName).serialize();
    console.log(queryString);

    $.ajax({
        url:'/vcn', //request 보낼 서버의 경로
        type:'post', // 메소드(get, post, put 등)
        data:queryString, //보낼 데이터
        success: function(data) {
            console.log(data);
        },
        error: function(err) {
            //서버로부터 응답이 정상적으로 처리되지 못햇을 때 실행
        }
    });

}