$(document).ready(function() {
    $(".lightbox").hide();
    $(".image-gallery img").click(function(e) {
        var img = $(e.target).clone();
        img.removeAttr("width");
        $(".lightbox").show();
        $(".lightbox").append(img)
    });
    $(".lightbox").click(function(e) {
        $(this).find("img").remove();
        $(this).hide();
    });
});