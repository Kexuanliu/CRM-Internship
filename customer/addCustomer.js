jQuery(document).ready(function () {
    var customerId = jQuery('#customerId').val();
    var addCustomerVue = new Vue({
        el: '#addCustomerVue',
        data: {
            showPage: 'addCustomer',
            schoolType: '',
            schoolTypeTmp:'',
            name:'',
            profile:'',
            website:'',
            schList: [],
            customerFrom: '',//客户来源
            customerFromTmp: '',//客户来源JS临时存储
            showErrMsg: false,
            errMsg: '',
            region: '',
            province: '',
            city: ''
        },
        methods: {
            //初始化方法, 用于编辑
            'init': function () {
                var thisVue = this;
                if (customerId.length > 0) {
                    $.ajax({
                        type: 'get',
                        url: '/customer/getSchool',
                        data: {
                            customerId: customerId
                        },
                        dataType: 'json',
                        cache: false,
                        success: function (result) {
                            if (result.successFlg) {
                                thisVue.schoolType = result.obj.customerType;
                                thisVue.schoolTypeTmp = result.obj.customerType;
                                thisVue.name = result.obj.customerName;
                                thisVue.profile = result.obj.profile;
                                thisVue.website = result.obj.website;
                                thisVue.customerFrom = result.obj.customerFrom;
                                thisVue.customerFromTmp = result.obj.customerFrom;
                            } else {
                                thisVue.errMsg = result.errMsg;
                                thisVue.showErrMsg = true;
                            }
                        }
                    });
                }


            },
            'selectSch':function () {
                this.showPage = 'selSchType';
            },
            'selectNam':function () {
                this.showPage = 'selName';
                this.searchSchool();
            },
            'clear': function () {
                this.name = '';
            },
            //选择客户来源
            'selectCustomerFrom': function () { //
                this.showPage = 'selectCustomerFromPage';
            },
            'cancel':function () {
                this.showPage = 'addCustomer';
                this.name ='';
            },
            'submit':function () {
                if(this.name ==''){
                    this.alertError("请填写学校名称！");
                    return;
                }
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: "/customer/checkExistByName",
                    data: {name: this.name},
                }).done(function (result) {
                    if (result.exist) {
                        thisVue.alertError("无法创建重名院校，请从以下搜索列表进入院校后创建二级学院");
                    } else {
                        thisVue.showPage = 'addCustomer';
                    }
                });
            },
            'subMit':function () {
                var url = "/customer/new";
                if (customerId.length > 0) {
                    url = "/customer/editSchool";
                }
                if(this.customerFrom==''){
                    this.alertError("请填写客户来源")
                    return;
                }
                if(this.name ==''|| this.schoolType ==''){
                    this.alertError("请填写学校类型或学校名称！");
                    return;
                }else{
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: url,
                        data: {
                            customerId: customerId,
                            schoolType:thisVue.schoolType,
                            name:thisVue.name,
                            profile:thisVue.profile,
                            website:thisVue.website,
                            customerFrom: thisVue.customerFrom,
                            region: thisVue.region,
                            province: thisVue.province,
                            city: thisVue.city
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result){
                        if (result.exist) {
                            thisVue.alertError("用户已经创建，请勿重复创建");
                        }else if(result.successFlg){
                            window.location.href="/customer/customerList";
                        } else {
                            thisVue.alertError("请填写正确的信息");
                        }
                    });
                };
            },
            'alertError': function (msg) {
                this.errMsg = msg;
                this.showErrMsg = true;
            },
            'selectType':function () {
                this.showPage = 'addCustomer';
            },
            'loadDetail': function (customer) {
                window.location.href = "/customer/customerInfo?customerId=" + customer.customerId;
            },
            'selectSchool':function () {
                this.showPage = 'addCustomer';
            },
            'cancelSelectSchoolType': function () {
                this.showPage = 'addCustomer';
            },
            'confirmSelectSchoolType': function () {
                if(this.schoolTypeTmp === ""){
                    this.alertError("学校类型不能为空！");
                    return;
                }
                this.schoolType = this.schoolTypeTmp;
                 this.showPage = 'addCustomer';
            },
            'confirmSelectCustomerFrom': function () {
                if (this.customerFromTmp === "") {
                    this.alertError("客户来源不能为空！");
                    return;
                }
                this.customerFrom = this.customerFromTmp;
                this.showPage = 'addCustomer';
            },
            'selectName':function () {
                this.showPage = 'addCustomer';
            },
            'transferTime': function (input) {
                if (input) {
                    return input.split(' ')[0];
                } else {
                    return '';
                }
            },
            'translateFrom': function (from) {
                switch (from) {
                    case "EXHIBITION":
                        return "展会";
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
            'translateType': function (from) {
                switch (from) {
                    case "SCHOOL":
                        return "学校";
                    case "COMPANY":
                        return "公司";
                    case "COLLEAGE":
                        return "高校";
                    case "VOCATIONSCHOOL":
                        return "高职";
                    case "SECONDARYSCHOOL":
                        return "中职";
                    case "k12":
                        return "k12";
                    case "AGENT":
                        return "代理商";
                    default:
                        return "未知";
                }
            },

            'searchSchool': function () {
                var thisVue = this;
                if(thisVue.name){
                    jQuery.ajax({
                        type: 'get',
                        url: '/customer/queryCustomer',
                        data: {
                            searchWord:thisVue.name,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result){
                        if (result.successFlg) {
                            thisVue.$set(thisVue, 'schList', result.customerList);
                        } else {
                            thisVue.errMsg = result.errMsg;
                        }
                    });
                }
            },
        },

        watch: {
            'name': function () {
                this.searchSchool();
            }
        }
    });
    addCustomerVue.init();
    $('#target').distpicker({
        province: "",
        city: "",
    });
});