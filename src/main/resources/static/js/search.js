function search() {
    var searchWord = $("#search-form").val();
    var href = "/products?filter=name:"+searchWord;
    doAjax(href,'get');
}