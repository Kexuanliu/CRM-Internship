jQuery(document).ready(function () {
    var agentId = jQuery('#agentId').val();
    var addAgentVue = new Vue({
        el: '#addAgentVue',
        data: {
            showPage: 'addAgent',
            showErrMsg: false,
            errMsg: '',
            linkObj: {
                agentId: '',
                linkUserId: '',
                linkName: '',
                linkPosition: '',
                linkGeneral: '',
                linkMobile: '',
                linkPhone: '',
                linkWeixin: '',
                linkQQ: '',
                linkMail: '',
                linkBg: '',
            }
        },
        methods: {
            //初始化方法, 用于编辑
            'init': function (agentId, linkUserId) {
                var thisVue = this;
                thisVue.linkObj.agentId = agentId;
                thisVue.linkObj.linkUserId = linkUserId;
                if (linkUserId.length > 0) {
                    $.ajax({
                        type: 'get',
                        url: '/customer/getAgentLink',
                        data: {
                            linkUserId: linkUserId
                        },
                        dataType: 'json',
                        cache: false,
                        success: function (result) {
                            if (result.successFlg) {
                                thisVue.linkObj = result.obj;
                            } else {
                                thisVue.errMsg = result.errMsg;
                                thisVue.showErrMsg = true;
                            }
                        }
                    });
                }
            },
            'subMit': function () {
                var linkUrl = "/customer/newAgentLink";
                if (this.linkObj.linkUserId.length > 0) {
                    linkUrl = "/customer/editAgentLink";
                }
                if (!this.linkObj.linkName) {
                    alert("请填写联系人名称！");
                    return;
                }
                if (!this.linkObj.linkPosition) {
                    alert("请填写联系人职位！");
                    return;
                }
                if (!this.linkObj.linkGeneral) {
                    alert("请填写联系人性别！");
                    return;
                }
                var thisVue = this;
                thisVue.linkObj.agentId = thisVue.linkObj.agentId;
                jQuery.ajax({
                    type: 'post',
                    url: linkUrl,
                    data: thisVue.linkObj,
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        window.location = '/customer/agentInfo?agentId=' + thisVue.linkObj.agentId;
                    }
                });
            },
            'goback': function () {
                history.back();
            },
        },
    });
    var linkUserId = $("#linkUserId").val();
    var agentId = $("#agentId").val();
    addAgentVue.init(agentId, linkUserId);
});