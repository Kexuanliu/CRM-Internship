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
            showEditDialog: false,
            companyName: '',
            showPage: '',
            errMsg:'',
            showErrMsg: false,
            showMainBar: true,
            agentLinkUser: [],
            showEditButton: false,
        },
        methods: {
            'orgDisplay':function () {
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                var temp = this;
                $loadingToast.fadeIn(100);
                setTimeout(function () {
                    $loadingToast.fadeOut(100);
                    location.href = '/customer/agentRelJour?agentId=' + temp.agentId + "&companyName=" + temp.companyName;
                }, 500);
            },
            'init': function (agentId, companyName, userType) {
                this.agentId = agentId;
                this.companyName = companyName;
                var thisVue = this;
                if (userType == 'ADMIN') {
                    this.showEditButton = true;
                }
                $.ajax({
                    type: 'get',
                    url: '/customer/getAgentLinkList',
                    data: {
                        agentId: thisVue.agentId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'agentLinkUser', result.crmAgentLinkList);
                        thisVue.loading = false;
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });

            },
            'toCustomerList':function () {
                window.location='/customer/customerList';
            },
            'toCustomerInfo':function () {
                window.location = '/customer/customerInfo?agentId=' + this.agentId;
            },
            'clickEditButton': function () {
                this.showEditDialog = true;
            },
            'cancelEditEvent': function () {
                this.showEditDialog = false;
            },
            'delCustomer': function () {
                var thisVue = this;
                var agentId = $("#agentId").val();
                if (confirm("是否删除代理商")) {
                    $.ajax({
                        type: 'get',
                        url: '/customer/delAgent',
                        data: {agentId: agentId},
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            window.location.href = "/customer/agentList";
                        } else {
                            thisVue.errMsg = result.errMsg;
                            thisVue.showErrMsg = true;
                        }
                    });
                }
            },
            'editCustomer': function () {
                var agentId = $("#agentId").val();
                window.location.href = "/customer/addAgent?agentId=" + agentId;
            },
            /////////////////////////////
            'checkGender': function (gender) {
                if (gender == 'FEMALE') {
                    return "/images/customer/FEMALE.svg";
                } else {
                    return "/images/customer/MALE.svg";
                }
            },
            'toContactDetail': function (linkUserId) {
                window.location = '/customer/agentLinkInfo?linkUserId=' + linkUserId;
            },
            'addLinker': function () {
                window.location = '/customer/addAgentLink?agentId=' + this.agentId;
            },
        }
    });

    var agentId = $("#agentId").val();
    var companyName = $("#companyName").val();
    var userType = $("#userType").val();
    customerVue.init(agentId, companyName ,userType);

});



