jQuery(document).ready(function () {
    var agentId = jQuery('#agentId').val();
    var addAgentVue = new Vue({
        el: '#addAgentVue',
        data: {
            showPage: 'addAgent',
            customerFromTmp: '',//客户来源JS临时存储
            cooperationTmp: '',
            cooperationTypeTmp: '',
            customerLevelTmp: '',
            showErrMsg: false,
            showEditButton: false,
            errMsg: '',
            obj: {
                //这里是代理模型
                agentId: '',
                companyName: '',
                profile: '',
                website: '',
                customerFrom: '',
                customerLevel: '',
                cooperation: '',
                cooperationType: '',
                region:'',
                province:'',
                city:''
            },
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
            'init': function (agentId,userType) {
                var thisVue = this;
                if (userType == 'ADMIN') {
                    this.showEditButton = true;
                }
                if (agentId.length > 0) {
                    $.ajax({
                        type: 'get',
                        url: '/customer/getAgent',
                        data: {
                            agentId: agentId
                        },
                        dataType: 'json',
                        cache: false,
                        success: function (result) {
                            if (result.successFlg) {
                                thisVue.obj = result.obj;
                                thisVue.linkObj = result.linkObj;
                            } else {
                                thisVue.errMsg = result.errMsg;
                                thisVue.showErrMsg = true;
                            }
                        }
                    });
                }
            },
            'selectSch': function () {
                this.showPage = 'selSchType';
            },
            //选择客户来源
            'selectCustomerFrom': function () { //
                this.showPage = 'selectCustomerFromPage';
            },
            'selectCooperation': function () {
                this.showPage = 'selectCooperationPage';
            },
            'selectCustomerLevel': function () {
                this.showPage = 'selectCustomerLevelPage';
            },
            'selectCooperationType': function () {
                this.showPage = 'selectCooperationTypePage';
            },
            'cancelSelect': function () {
                this.showPage = 'addAgent';
            },
            'alertError': function (msg) {
                this.errMsg = msg;
                this.showErrMsg = true;
            },
            'subMit': function () {
                var url = "/customer/newAgent";
                var linkUrl = "/customer/newAgentLink";
                if (agentId.length > 0) {
                    url = "/customer/editAgent";
                    linkUrl = "/customer/editAgentLink";
                }
                if (!this.obj.companyName) {
                    this.alertError("请填写公司名称！");
                    return;
                }
                if (!this.obj.profile) {
                    this.alertError("请填写公司背景！");
                    return;
                }
                if (!this.obj.customerFrom) {
                    this.alertError("请填写客户来源！");
                    return;
                }
                if (!this.obj.customerLevel) {
                    this.alertError("请填写客户级别！");
                    return;
                }
                if (!this.obj.cooperation) {
                    this.alertError("请填写合作意向！");
                    return;
                }
                if (!this.obj.cooperationType) {
                    this.alertError("请填写合作类型！");
                    return;
                }
                if (!this.linkObj.linkName) {
                    this.alertError("请填写联系人名称！");
                    return;
                }
                if (!this.linkObj.linkPosition) {
                    this.alertError("请填写联系人职位！");
                    return;
                }
                if (!this.linkObj.linkGeneral) {
                    this.alertError("请填写联系人性别！");
                    return;
                }
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: url,
                        data: thisVue.obj,
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.exist) {
                            thisVue.alertError("用户已经创建，请勿重复创建");
                        } else if (result.successFlg) {
                            if(thisVue.showEditButton){
                                thisVue.linkObj.agentId = result.agentId;
                                jQuery.ajax({
                                    type: 'post',
                                    url: linkUrl,
                                    data: thisVue.linkObj,
                                    dataType: 'json',
                                    cache: false
                                }).done(function (result) {
                                    if (result.successFlg) {
                                        window.location.href = "/customer/agentList";
                                    }else{
                                        thisVue.alertError(result.errMsg);
                                    }
                                });
                            }else{
                                window.location.href = "/customer/agentList";
                            }
                        } else {
                            thisVue.alertError(result.errMsg);
                        }
                    });
            },
            'confirmSelectCustomerFrom': function () {
                if (!this.customerFromTmp) {
                    this.alertError("客户来源不能为空！");
                    return;
                }
                this.obj.customerFrom = this.customerFromTmp;
                this.showPage = 'addAgent';
            },
            'confirmSelectCooperation': function () {
                if (!this.cooperationTmp) {
                    this.alertError("合作意向不能为空！");
                    return;
                }
                this.obj.cooperation = this.cooperationTmp;
                this.showPage = 'addAgent';
            },
            'confirmSelectCustomerLevel': function () {
                if (!this.customerLevelTmp) {
                    this.alertError("客户级别不能为空！");
                    return;
                }
                this.obj.customerLevel = this.customerLevelTmp;
                this.showPage = 'addAgent';
            },
            'confirmCooperationType': function () {
                if (!this.cooperationTypeTmp) {
                    this.alertError("合作模式不能为空！");
                    return;
                }
                this.obj.cooperationType = this.cooperationTypeTmp;
                this.showPage = 'addAgent';
            },
            'selectName': function () {
                this.showPage = 'addAgent';
            },
            'translateFrom': function (from) {
                switch (from) {
                    case "HIGHEREDUCATION":
                        return "高教展";
                    case "GOLDCOMPETITION":
                        return "金砖赛";
                    case "INTRODUCTION":
                        return "转介绍";
                    case "INTERNET":
                        return "网络";
                    case "COMPANY":
                        return "公司";
                    default:
                        return "未知";
                }
            },
            'translateCooperationType': function (type) {
                switch (type) {
                    case "SPARATE":
                        return "合作分成";
                    case "PROCUREMENT":
                        return "采购模式";
                    case "CONTROLSTANDARD":
                        return "硬件控标";
                    default:
                        return "未知";
                }
            },
            'translateCustomerLevel': function (level) {
                switch (level) {
                    case "CORE":
                        return "核心";
                    case "EXCELLENT":
                        return "优质";
                    case "MIDDLE":
                        return "中等";
                    case "NORMAL":
                        return "普通";
                    default:
                        return "未知";
                }
            },
            'translateCooperation': function (input) {
                switch (input) {
                    case "STRONG":
                        return "强烈";
                    case "MIDDLE":
                        return "中等";
                    case "NORMAL":
                        return "普通";
                    case "WEAK":
                        return "弱";
                    default:
                        return "未知";
                }
            },
        },
    });
    var userType = $("#userType").val();
    var agentId=$("#agentId").val();
    addAgentVue.init(agentId,userType);

    $('#target').distpicker({
        province: "",
        city: "",
    });
});