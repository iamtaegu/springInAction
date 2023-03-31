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