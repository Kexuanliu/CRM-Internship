Vue.component('navfooter', {
    data: function () {
        var res = document.location.href;
        var showClass = "customerList";
        if (res.indexOf("customerList") > 0) {
            showClass = "customerList";
        } else if (res.indexOf("agentList") > 0) {
            showClass = "customerList";
        } else if (res.indexOf("opportunity") > 0) {
            showClass = "opportunity";
        } else if (res.indexOf("toList") > 0) {
            showClass = "toList";
        } else if (res.indexOf("myAccountInfor") > 0) {
            showClass = "myAccountInfor";
        }
        return {
            showClass: showClass,
            customerList: 'customerList',
            opportunity: 'opportunity',
            toList: 'toList',
            myAccountInfor: 'myAccountInfor'
        }
    },
    methods: {
        judgeShowClass: function (input) {
            if (input == this.showClass) {
                return "weui-tabbar__item weui-bar__item_on";
            } else {
                return "weui-tabbar__item";
            }
        },
        getImgUrl: function (input) {
            var head = "";
            var type = 1;
            switch (input) {
                case "customerList":
                    head="/images/bottom/customer";
                    break;
                case "opportunity":
                    head="/images/bottom/opportunity";
                    break;
                case "toList":
                    head="/images/bottom/journal";
                    break;
                case "myAccountInfor":
                    head="/images/bottom/mine";
                    break;
            }
            if (input == this.showClass) {
                type = 2;
            }
            return head + "_" + type + ".svg";
        }
    },
    template: '<div class="weui-tabbar">\n' +
        '        <a href="/customer/customerList" v-bind:class="judgeShowClass(customerList)">\n' +
        '            <img v-bind:src="getImgUrl(customerList)" class="weui-tabbar__icon">\n' +
        '            <p class="weui-tabbar__label">客户</p>\n' +
        '        </a>\n' +
        '        <a href="/opportunity"  v-bind:class="judgeShowClass(opportunity)">\n' +
        '            <img v-bind:src="getImgUrl(opportunity)" class="weui-tabbar__icon">\n' +
        '            <p class="weui-tabbar__label">商机</p>\n' +
        '        </a>\n' +
        '        <a href="/journal/toList"  v-bind:class="judgeShowClass(toList)">\n' +
        '            <img v-bind:src="getImgUrl(toList)" class="weui-tabbar__icon">\n' +
        '            <p class="weui-tabbar__label">日志</p>\n' +
        '        </a>\n' +
        '        <a href="/myAccountInfor"  v-bind:class="judgeShowClass(myAccountInfor)">\n' +
        '            <img v-bind:src="getImgUrl(myAccountInfor)" class="weui-tabbar__icon">\n' +
        '            <p class="weui-tabbar__label">更多</p>\n' +
        '        </a>\n' +
        '    </div>'
})
new Vue({el: '#footer'})