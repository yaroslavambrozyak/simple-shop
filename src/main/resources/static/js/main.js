
$(".ajax-url").on("click",function (event) {
    event.preventDefault();
    var href = $(this).attr('href')
    $.ajax({
        url:href
    }).done(function (data) {
        var main = $(".main-container");
        main.empty();
        main.html(data);
        window.history.pushState("obj", "title", href);
    }).fail(function () {

    })
})