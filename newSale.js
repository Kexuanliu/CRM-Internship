/**
 * Created by Administrator on 2018/8/1.
 */

$(document).ready(function () {

    function handleTime(time) {
        if (time < 10)
            return "0" + time;
        else
            return time;
    }

    var saleVue = new Vue({
        el: '#saleVue',
        data: {
            showErrMsg: false,
            errMsg: '',
            stageList:stageListData,
            ind: ['A', 'B', 'C', 'D', 'F'],
            showPage: 'basicInfo',
            preDate: '请选择',
            deliverDate: '请选择',
            saleStage: '',
            agentLinkShow: '请选择',
            shcoolShow: '请选择',
            selStage: '',
            lastStage: '',
            contact: '请选择',
            content: '',
            temp: '',
            customerType: 1,
            contactId: '',
            serachAgentWord: '',
            searchSchoolWord:'',
            agentSourceTmp: [],
            makeContact: '请选择',
            chargeContact: '请选择',
            makeContactId: '',
            chagerContactId: '',
            opportunityName: '',
            amount: '',
            makeCustomerId: '',
            chargeCustomerId: '',
            tmpDepartId: '',
            agentInfo: {
                total: '',
                agentLinkId: '',
                agentId: ''
            },
            makeinfo: {
                total: '',
                customerId: ''
            },
            schoolInfo: {
                schoolTotal: '',
                lines: [],
                makeLinks: [],
                chargeLinks: []
            },
            decisionMaker: '',//决策者
            charge: '', //负责人
            agentLinker: '代理商',
            myCustomers: [{
                showSub: false,
                imgPath: "/images/customer/fold.svg"
            }],
            agentList: [{
                crmAgent: {},
                crmAgentLinkList: [],
                showSub: false,
                imgPath: "/images/customer/fold.svg"
            }],
            errMsg: '',
            keyWord: '',
            containXuebei: 2,
            moneyFrom: ""
        },
        methods: {
            'back': function () {
                window.location = '/opportunity';
            },
            'showDatePicker': function () {
                var thisVue = this;
                var nowDate = new Date();
                weui.datePicker({
                    start: 1990,
                    end: 2030,
                    defaultValue: [nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate()],
                    onChange: function (result) {
                        console.log(result);
                    },
                    onConfirm: function (result) {
                        thisVue.preDate = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
                        console.log(result);
                    }
                });
            },
            'deliverDatePicker': function () {
                var thisVue = this;
                var nowDate = new Date();
                weui.datePicker({
                    start: 1990,
                    end: 2030,
                    defaultValue: [nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate()],
                    onChange: function (result) {
                        console.log(result);
                    },
                    onConfirm: function (result) {
                        thisVue.deliverDate = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
                        console.log(result);
                    }
                });
            },
            'backToInfo': function () {
                this.showPage = 'basicInfo';
            },
            'selSaleStage': function () {
                this.showPage = 'saleStage';
            },
            'done1': function () {
                if (this.selStage === "") {
                    alert("销售阶段不能为空！");
                    return;
                } else if (this.selStage === 'F') {
                    this.saleStage = '输单';
                } else {
                    this.saleStage = this.selStage + '阶段';
                }
                this.lastStage = this.selStage;
                this.showPage = 'basicInfo';
            },
            'done2': function () {
                if (this.temp === "") {
                    alert("客户联系人不能为空！");
                    return;
                }
                var str = this.temp.split(':');
                if (this.customerType === 1) {
                    this.makeContact = str[0];
                    this.makeContactId = str[1];
                    this.makeCustomerId = str[2];
                } else {
                    this.chargeContact = str[0];
                    this.chagerContactId = str[1];
                    this.chargeCustomerId = str[2];
                }
                this.showPage = 'basicInfo';
            },
            'chooseSchool':
                function () {
                    if (this.schoolInfo.schoolTotal) {
                        console.log(this.schoolInfo);
                        this.shcoolShow = this.schoolInfo.schoolTotal;
                        var thisVue = this;
                        var deptId = thisVue.tmpDepartId;
                        $.ajax({
                            type: 'get',
                            url: '/opportunity/getDepartMentLinks',
                            data: {deptId: deptId},
                            dataType: 'json',
                            cache: false
                        }).done(function (result) {
                            if (result.successFlg) {
                                thisVue.schoolInfo.makeLinks = result.item.contactsList;
                                thisVue.schoolInfo.chargeLinks = result.item.contactsList;
                            } else {
                                thisVue.errMsg = result.errMsg;
                                thisVue.showErrMsg = true;
                            }
                        });
                        this.showPage = 'basicInfo';
                    } else {
                        alert("请选择院校");
                    }
                }
            ,
            'sumName':
                function (schoolName, depName) {
                    return schoolName + '-' + depName;
                }
            ,
            'clickSchool':
                function (contact) {
                    this.tmpDepartId = contact.deptId;
                }
            ,
            'chooseAgent': function () {
                if (this.agentInfo.total) {
                    var res = this.agentInfo.total.split('|');
                    this.agentInfo.agentLinkId = res[1];
                    this.agentInfo.agentId = res[2];
                    this.agentLinkShow = res[0];
                } else {
                    alert("请选择联系人");
                }
                console.log(this.agentInfo);
                this.showPage = 'basicInfo';
            },
            'selCustomer': function () {
                this.showPage = 'customerContact';
                this.showMyCustomers();
            },
            'selCustomerLink':
                function (input) {
                    if(!this.tmpDepartId){
                        this.alertError("请先选择学院及二级学院");
                        return;
                    }
                    if((this.schoolInfo.makeLinks==null || this.schoolInfo.makeLinks.length==0)){
                        this.alertError("请先在该二级学院下设置人员");
                        return;
                    }
                    if (input === 1) {
                        this.customerType = input;
                        this.showPage = 'customerContactList';
                    } else {
                        this.customerType = 2;
                        this.showPage = 'customerContactList2';
                    }
                }
            ,
            'alertError': function (msg) {
                this.errMsg = msg;
                this.showErrMsg = true;
            },
            'selAgent': function () {
                this.showPage = "agentContact";
                this.showMyAgents();
            },
            'showMyAgents': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/getAgentLinks',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'agentList', result.agentList);
                        for (var index in thisVue.agentList) {
                            var tmpVue = thisVue.agentList[index];
                            thisVue.$set(tmpVue, 'showSub', false);
                            thisVue.$set(tmpVue, 'imgPath', "/images/customer/fold.svg");
                            for (var index2 in tmpVue.crmAgentLinkList) {
                                thisVue.$set(tmpVue.crmAgentLinkList[index2], 'totalName',
                                    tmpVue.crmAgent.companyName + '-' + tmpVue.crmAgentLinkList[index2].linkName + '|' + tmpVue.crmAgentLinkList[index2].linkUserId + '|' + tmpVue.crmAgent.agentId);
                            }
                        }
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'checkNull': function () {
                if (this.makeContactId === "" || this.chagerContactId === "" ||
                    this.lastStage === "" || this.opportunityName === "" || this.moneyFrom === "") {
                    alert("以下带星号内容均为必填项！");
                    return false;
                } else {
                    if (this.preDate === "请选择") {
                        this.preDate = '';
                    }
                    if (this.deliverDate === "请选择") {
                        this.deliverDate = '';
                    }
                    return true;
                }
            },
            'add': function () {
                if (!this.checkNull()) {
                    return;
                }
                var thisVue = this;
                var postData = {
                    customerId: this.makeCustomerId,
                    opportunityName: this.opportunityName,
                    salesStatus: this.lastStage,
                    amount: this.amount,
                    checkDate: this.preDate,
                    clinchDate: this.deliverDate,
                    content: this.content,
                    chargeId: this.chagerContactId,
                    decisionMakerId: this.makeContactId,
                    agentId: this.agentInfo.agentId,
                    agentLinkId: this.agentInfo.agentLinkId,
                    containXuebei: this.containXuebei,
                    moneyFrom: this.moneyFrom
                };
                $.ajax({
                    type: 'post',
                    url: '/opportunity/addSale',
                    data: JSON.stringify(postData),
                    dataType: 'json',
                    contentType: 'application/json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        $('#toast').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            window.location = '/opportunity';
                        }, 1000);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'search': function () {
                //this.customers = true;   逻辑待修改
                //window.location.href = "/customer/customer?customerName=" + this.keyWord;
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchInput').focus();
            },
            'hideSearchResult': function () {
                $('#searchResult').hide();
                this.keyWord = "";
            },
            'cancelSearch': function () {
                this.hideSearchResult();
                $('#searchBar').removeClass('weui-search-bar_focusing');
            },
            'clear': function () {
                this.keyWord = "";
                $('#searchInput').focus();
            },
            'cancel': function () {
                this.cancelSearch();
                $('#searchInput').blur();
            },
            'showMyCustomers': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/getCustomers',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'myCustomers', result.customerList);
                        for (var index in thisVue.myCustomers ) {// 0; index < thisVue.myCustomers.length; index++
                            thisVue.$set(thisVue.myCustomers[index], 'showSub', false);
                            thisVue.$set(thisVue.myCustomers[index], 'imgPath', "/images/customer/fold.svg");
                        }
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                });
            },
            'setImgPath': function (index) {
                if (this.myCustomers[index].showSub == false) {
                    this.$set(this.myCustomers[index], 'imgPath', "/images/customer/fold.svg");
                } else {
                    this.$set(this.myCustomers[index], 'imgPath', "/images/customer/unfold.svg");
                }
            },
            'changeSubFold': function (sub) {
                sub.showSub = !sub.showSub;
            },
            'changeAgentSubFold': function (sub) {
                sub.showSub = !sub.showSub;
            },
            'onTransferValue': function (customerInfo) {
                this.temp = customerInfo;
            },
            'checkGender': function (gender) {
                if (gender == 'FEMALE') {
                    return "/images/customer/FEMALE.svg";
                } else {
                    return "/images/customer/MALE.svg";
                }
            },
            'addNumBrackets': function (number) {
                if (number == '0') {
                    return '';
                } else {
                    return "( " + number + " )";
                }
            }
        },
        computed: {
            'filterAgentList': function () {
                var key = this.serachAgentWord;
                if (this.agentList.length > 1) {
                    var agentListTmp = this.agentList;
                    return agentListTmp.filter(function (item) {
                        return (item.crmAgent.companyName.indexOf(key) != -1 || item.crmAgent.createName.indexOf(key) != -1)
                    });
                } else {
                    return this.agentList;
                }
            },
            'filterSchoolList': function () {
                var key = this.searchSchoolWord;
                if (this.myCustomers.length > 1) {
                    var schoolTmp = this.myCustomers;
                    return schoolTmp.filter(function (item) {
                        return (item.customerName.indexOf(key) != -1)
                    });
                } else {
                    return this.myCustomers;
                }
            }
        }
    });
});