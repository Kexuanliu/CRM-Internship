/**
 * Created by Administrator on 2018/7/18.
 */
var color = ["", "#2eff4e", "#2b79ff", "#ff7e24"];
var percent = [0, 80, 75, 90];
$(document).ready(function ()  {

    var customerVue = new Vue({
        el: '#customerVue',
        data: {
            customerName: '',
            agentId: '',
            linkUserId: '',
            showEditDialog: false,
            showEditButton: false,
            companyName: '',
            showPage: '',
            errMsg:'',
            showErrMsg: false,
            showMainBar: true,
            agentLinkUser:[],
        },
        methods: {
            'orgDisplay':function () {
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                var temp = this;
                $loadingToast.fadeIn(100);
                setTimeout(function () {
                    $loadingToast.fadeOut(100);
                    location.href = '/customer/organization?agentId=' + temp.agentId;
                }, 500);
            },
            'init': function (linkUserId,userType) {
                this.linkUserId = linkUserId;
                if (userType == 'ADMIN') {
                    this.showEditButton = true;
                }
            },
            'clickEditButton': function () {
                this.showEditDialog = true;
            },
            'cancelEditEvent': function () {
                this.showEditDialog = false;
            },
            'delCustomer': function () {
                var thisVue = this;
                var linkUserId = $("#linkUserId").val();
                if (confirm("是否删除联系人")) {
                    $.ajax({
                        type: 'get',
                        url: '/customer/delAgentLink',
                        data: {linkUserId: linkUserId},
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            history.back();
                        } else {
                            thisVue.errMsg = result.errMsg;
                            thisVue.showErrMsg = true;
                        }
                    });
                }
            },
            'editCustomer': function () {
                var linkUserId = $("#linkUserId").val();
                window.location.href = "/customer/addAgentLink?linkUserId=" + linkUserId;
            },
        }
    });
    var linkUserId = $("#linkUserId").val();
    var userType = $("#userType").val();
    customerVue.init(linkUserId, userType);

});



