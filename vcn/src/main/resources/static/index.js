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
    var resultStr;

    $.ajax({
        url:'/vcn', //request 보낼 서버의 경로
        type:'post', // 메소드(get, post, put 등)
        data:queryString, //보낼 데이터
        success: function(data) {
            $(".view").html();
            resultStr = '<pre style="font: bold italic 2.0em/1.0em 돋움체;word-break:break-all;">{\n'
            $.each(data.responseStatus, function(key, val) {
                    resultStr += "\t" + key + ":" + val;
                    resultStr += ",\n";
                }
            )
            resultStr = resultStr.substring(0, resultStr.length-1);
            resultStr += '\n}</pre>';

            $(".view").html(resultStr);
        },
        error: function(err) {
            //서버로부터 응답이 정상적으로 처리되지 못햇을 때 실행
        }
    });

}