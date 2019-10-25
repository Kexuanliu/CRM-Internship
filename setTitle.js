Vue.component('settitle', {
    props: {
        name: String
    },
    template: '<div style="background-color: white">\n' +
        '    <div class="navi-bar">\n' +
        '        <div class="top-bar center-box">\n' +
        '                <span class="pull-left" @click="history.back()">\n' +
        '                    <img src="/images/back_icon.svg" style="margin-top: 7px; margin-left: 10px">\n' +
        '                </span>' + '<span style="margin-top: 3px;">{{name}}</span>' +
        '<span class="pull-right blueColor" style="margin-right: 5px; font-size: 16px; padding: 2px">导出</span>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <div style="height: 44px"></div>'
})
new Vue({el: '#title'})

