$(".ajax-url").on("click",function (event) {
    event.preventDefault();
    var href = $(this).attr('href')
    doAjax(href,'get');
});

function doAjax(href,type) {
    $.ajax({
        url:href,
        type:type.toUpperCase()
    }).done(function (data) {
        var main = $(".main-container");
        main.empty();
        main.html(data);
        window.history.pushState("obj", "title", href);
    }).fail(function () {

    });
}