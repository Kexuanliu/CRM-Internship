
new Vue ({
    el: '#setLink',
    data: {
        link: "https://dev.walkclass.com/downloadApp.jsp;JSESSIONID=1501d0a0-cfe9-4baa-85ef-a99fe406d758D=1501d0a0-cfe9…"
    },
});

var clipboard = new ClipboardJS('.copyPasteButton');
clipboard.on('success', function(e) {
    weui.toast("复制成功", 2000);
});
clipboard.on("error", function(e) {
    weui.toast("复制失败，请手动复制", "forbidden");
})

function back() {
    window.history.back();
}

