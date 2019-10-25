
jQuery(document).ready(function () {

    Vue.component('show-list', {
        template: '#showList',
        props: ['data'],
        data: function () {
            return {
                orderType: 'desc'
            };
        },
        methods: {
            sortByField: function (filed) {
                this.data.tableBody.sort(compare(filed, 'value', this.orderType));
                if (this.orderType == 'desc') {
                    this.orderType = 'asc';
                } else {
                    this.orderType = 'desc';
                }
            }
        }
    });

    var statisticVue = new Vue({
        el: '#dataVue',
        data: {
            showPage: 'mainPage',//mainPage
            detailName: '新增商机',
            detailType: 'newOpp',
            employee: {
                serachAllEmployeeWord: "",
                serachSubEmployeeWord: "",
                selAll: false,
                selSubAll: false,
                allEmployee: [],
                subEmployee: [],
                choosenList: [],
                finnalList: [],
            },
            timeChoose: {
                beiginTime: null,
                endTime: null,
                timeEnum: "thisMonth"
            },
            dataList: {
                //表头
                tableHead: [
                    {
                        name: '新增时间',
                        sortFiled: 'createTime'
                    },
                    {
                        name: '员工',
                        sortFiled: 'employee'
                    },
                    {
                        name: '院校',
                        sortFiled: ''
                    },
                    {
                        name: '二级学院',
                        sortFiled: ''
                    },
                ],
                tableBody: [
                    {
                        createTime: {
                            value: '2019-1-6',
                        },
                        employee: {
                            value: '刘洋2',

                        },
                        school: {
                            value: '浙江大学',
                            link: 'http://www.baidu.com'
                        },
                        subPart: {
                            value: '机械学院',
                            link: ''
                        }
                    },
                    {
                        createTime: {
                            value: '2019-1-3',
                            link: ''
                        },
                        employee: {
                            value: '刘洋1',
                            link: ''
                        },
                        school: {
                            value: '浙江大学',
                            link: 'http://www.baidu.com'
                        },
                        subPart: {
                            value: '机械学院',
                            link: ''
                        }
                    },
                    {
                        createTime: {
                            value: '2019-1-4',
                            link: ''
                        },
                        employee: {
                            value: '刘洋3',
                            link: ''
                        },
                        school: {
                            value: '浙江大学',
                            link: 'http://www.baidu.com'
                        },
                        subPart: {
                            value: '机械学院',
                            link: ''
                        }
                    }
                ]
            },
            totalStatistic: {
                aLevelCustomerCount: 0,
                aLevelCustomerRate: 0,
                newAgentCount: 0,
                coreAgentCount: 0,
                visitCustomerTimesCount: 0,
                visitCustomerNumCount: 0,
                accompanyVisitTimesCount: 0,
                accompanyVisitNumCount: 0,
                deepVisitTimesCount: 0,
                normalVisitTimesCount: 0,
                customerVisitNumCount: 0,
                deptVisitNumCount: 0,
                visitPresidentTimesCount: 0,
                visitDeanTimesCount: 0,
                newOppCount: 0,
                alevelOppCount: 0,
                blevelOppCount: 0,
                clevelOppCount: 0,
                dlevelOppCount: 0,
                dlevelOppMoneySum: 0
            }
        },
        methods: {
            back: function () {
                history.back();
            },
            init: function () {
                var tmpVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/member/getStatsticMemberList',
                    data: {},
                    dataType: 'json',
                    cache: false,
                    success: function (result) {
                        if (!result.successFlg) {
                            alert("获取人员数据错误, 请联系管理员");
                        } else {
                            tmpVue.employee.allEmployee = result.allMember;
                            tmpVue.employee.subEmployee = result.subMember;
                        }
                    }
                });
                var employee_str = window.localStorage.getItem("static_employee");
                if (employee_str != null && employee_str != undefined && employee_str != '') {
                    this.employee = JSON.parse(employee_str);
                }
                var time_sel_str = window.localStorage.getItem("static_time_sel");
                if (time_sel_str != null && time_sel_str != undefined && time_sel_str != '') {
                    this.timeChoose = JSON.parse(time_sel_str);
                }
                this.getStatisticsAll();
            },
            buildParam: function () {
                var param = {
                    userIds: [],
                    timeSpanEnum: "",
                    beginTime: null,
                    endTime: null
                };
                for (var i = 0; i < this.employee.finnalList.length; i++) {
                    param.userIds.push(this.employee.finnalList[i].userId);
                }
                param.timeSpanEnum = this.timeChoose.timeEnum;
                param.beginTime = this.timeChoose.beiginTime;
                param.endTime = this.timeChoose.endTime;
                return param;
            },
            getStatisticsAll: function () {
                var param = this.buildParam();
                var thisVue = this;
                if (param.userIds && param.userIds.length > 0 && param.timeSpanEnum) {
                    var $loadingToast = $('#loadingToast');
                    if ($loadingToast.css('display') != 'none') return;
                    $loadingToast.fadeIn(100);
                    jQuery.ajax({
                        type: 'get',
                        url: '/statistics/getAllStatistics',
                        data: {
                            userIds: param.userIds,
                            timeSpanEnum: param.timeSpanEnum,
                            beiginTime: param.beginTime,
                            endTime: param.endTime
                        },
                        traditional:true,
                        dataType: 'json',
                        cache: false,
                        success: function (result) {
                            if (!result.successFlg) {
                                alert("初始化出错");
                            } else {
                                thisVue.totalStatistic = result.totalView;
                                $loadingToast.fadeOut(100);
                            }
                        }
                    });
                }

            },
            backToMain: function () {
                this.showPage = "mainPage";
            },
            backToAllList:function(){
                this.showPage = "allList";
            },
            showTimeSel: function () {
                this.showPage = "chooseTime";
            },
            showSelectAllList: function () {
                this.showPage = "allList";
            },
            showSelectSubList: function () {
                this.showPage = "subList";
            },
            showSelectedList:function(){
                this.showPage = 'finalList';
                var str = JSON.stringify(this.employee.choosenList);
                this.employee.finnalList = JSON.parse(str);
            },
            confirmSelList: function () {
                var str = JSON.stringify(this.employee.choosenList);
                this.employee.finnalList = JSON.parse(str);
                var employee_str = JSON.stringify(this.employee);
                window.localStorage.setItem("static_employee", employee_str);
                this.showPage = "mainPage";
            },
            confirmTimeSel:function(){
                var time_sel_str = JSON.stringify(this.timeChoose);
                window.localStorage.setItem("static_time_sel", time_sel_str);
                this.showPage = "mainPage";
            },
            confirmFinalList: function () {
                var str = JSON.stringify(this.employee.finnalList);
                this.employee.choosenList = JSON.parse(str);
                this.showPage = "allList";
            },
            showChooseTime: function () {
                this.showPage = "chooseTime";
            },
            logData: function () {
                console.log(this.data);
            },
            setAllFalse: function () {
                this.employee.selAll = false;
            },
            setSubFalse: function () {
                this.employee.selSubAll = false;
            },
            setTimeEnum: function (input) {
                this.timeChoose.timeEnum = input;
            },
            exportExcel: function () {
                var isMac = navigator.platform.toUpperCase().indexOf('MAC') >= 0;
                var is_iOS = navigator.platform.match(/(iPhone|iPod|iPad)/i) ? true : false;
                if (isMac || is_iOS) {
                    window.location = '/statistics/export';
                } else {
                    window.location = "https://dev.walkclass.com/downloadApp.jsp;JSESSIONID=1501d0a0-cfe9-4baa-85ef-a99fe406d758D=1501d0a0-cfe9…";
                }
            },
            getDetails: function (type) {
                this.detailType = type;
                this.showPage = 'showList';
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                $loadingToast.fadeIn(100);
                var param = this.buildParam();
                var thisVue = this;
                if (param.userIds && param.userIds.length > 0 && param.timeSpanEnum) {
                    jQuery.ajax({
                        type: 'get',
                        url: '/statistics/getDetails',
                        data: {
                            userIds: param.userIds,
                            timeSpanEnum: param.timeSpanEnum,
                            beiginTime: param.beginTime,
                            endTime: param.endTime,
                            statisticsTypeEnum: this.detailType
                        },
                        traditional: true,
                        dataType: 'json',
                        cache: false,
                        success: function (result) {
                            if (!result.successFlg) {
                                alert("获取详情出错");
                            } else {
                                switch (thisVue.detailType) {
                                    case "newACustomer": case "ACover":
                                        thisVue.dataList = buildCustomerDataList(result.res);
                                        break;
                                    case "newAgent": case "coreAgent":
                                        thisVue.dataList = buildAgentDataList(result.res);
                                        break;
                                    case "visitCustomer": case "companyVisit":
                                    case "deepVisit": case "normalVisit":
                                    case "visistCustomer": case "visistDept":
                                    case "visitPresident": case "visitDean":
                                        thisVue.dataList = buildVisitDataList(result.res);
                                        break;
                                    case "newOpp": case "newAOpp":
                                    case "newBOpp": case "newCOpp":
                                    case "newDOpp": case "DMoney":
                                        thisVue.dataList = buildOppDataList(result.res);
                                        break;
                                }
                                $loadingToast.fadeOut(100);
                            }
                        }
                    });
                }
            },
        },
        computed: {
            filterAllEmployeeList: function () {
                var key = this.employee.serachAllEmployeeWord;
                var allList = this.employee.allEmployee;
                if (allList != null && allList.length > 0) {
                    return allList.filter(function (item) {
                        return (item.realName.indexOf(key) != -1);
                    });
                } else {
                    return allList;
                }
            },
            filterSubEmployeeList: function () {
                var key = this.employee.serachSubEmployeeWord;
                var allList = this.employee.subEmployee;
                if (allList != null && allList.length > 0) {
                    return allList.filter(function (item) {
                        return (item.realName.indexOf(key) != -1);
                    });
                } else {
                    return allList;
                }
            },
            calChoosePeople: function () {
                var tmp = this.employee.choosenList;
                if (tmp.length == 0) {
                    return "0人";
                } else if (tmp.length == 1) {
                    return tmp[0].realName;
                } else {
                    return tmp[0].realName + "等" + tmp.length + "人";
                }
            },
            translateTimeEnum: function () {
                switch (this.timeChoose.timeEnum) {
                    case "today":
                        return "今天";
                    case "yesterday":
                        return "昨天";
                    case "thisMonth":
                        return "本月";
                    case "lastMonth":
                        return "上月";
                    case "thisQuarter":
                        return "本季度";
                    case "lastQuarter":
                        return "上季度";
                    case "thisYear":
                        return "今年";
                    case "lastYear":
                        return "去年";
                    case "toNow":
                        return "注册至今";
                }
            }
        },
        watch: {
            'employee.selAll': function () {
                if (this.employee.selAll) {
                    var str = JSON.stringify(this.employee.allEmployee);
                    this.employee.choosenList = JSON.parse(str);
                } else {
                    this.employee.choosenList = [];
                }
            },
            'employee.selSubAll': function () {
                if (this.employee.selSubAll) {
                    for (var i = 0; i < this.employee.subEmployee.length; i++) {
                        this.employee.choosenList.push(this.employee.subEmployee[i]);
                    }
                } else {
                    this.employee.choosenList = [];
                }
            },
            'timeChoose.timeEnum':function () {
                this.getStatisticsAll();
            },
            'timeChoose.endTime':function () {
                this.getStatisticsAll();
            },
            'timeChoose.beiginTime':function () {
                this.getStatisticsAll();
            },
            'employee.finnalList':function () {
               this.getStatisticsAll();
            },
            'detailType': function () {
                switch (this.detailType) {
                    case "newACustomer":
                        this.detailName = '新增A级人员';
                        break;
                    case "ACover":
                        this.detailName = "A级人员覆盖率";
                        break;
                    case "newAgent":
                        this.detailName = "新增代理商";
                        break;
                    case "coreAgent":
                        this.detailName = "核心代理商";
                        break;
                    case "visitCustomer":
                        this.detailName = "拜访客户";
                        break;
                    case "companyVisit":
                        this.detailName = "陪同拜访";
                        break;
                    case "deepVisit":
                        this.detailName = "深度拜访";
                        break;
                    case "normalVisit":
                        this.detailName = "浅度拜访";
                        break;
                    case "visistCustomer":
                        this.detailName = "拜访一级学院";
                        break;
                    case "visistDept":
                        this.detailName = "拜访二级学院";
                        break;
                    case "visitPresident":
                        this.detailName = "拜访院长";
                        break;
                    case "visitDean":
                        this.detailName = "拜访主任";
                        break;
                    case "newOpp":
                        this.detailName = "新增商机";
                        break;
                    case "newAOpp":
                        this.detailName = "新增A级";
                        break;
                    case "newBOpp":
                        this.detailName = "新增B级";
                        break;
                    case "newCOpp":
                        this.detailName = "新增C级";
                        break;
                    case "newDOpp":
                        this.detailName = "新增D级";
                        break;
                    case "DMoney":
                        this.detailName = "D级计划成交金额"
                        break;
                }
            }
        }
    });
    statisticVue.init();
});

function compare(name, name2, sortType) {
    return function (o, p) {
        var a, b;
        var sortResA = -1;
        var sortResB = 1;
        if (sortType == 'desc') {
            sortResA = 1;
            sortResB = -1;
        }
        if (o && p && typeof o === 'object' && typeof p === 'object') {
            a = o[name][name2];
            b = p[name][name2];
            if (typeof a === typeof b) {
                return a < b ? sortResA : sortResB;
            }
            return typeof a < typeof b ? sortResA : sortResB;
        } else {
            alert("error");
        }
    }
}


//构建商机详情列表
function buildOppDataList(resList) {
    var dataList = {};
    var tableHead = [
        {
            name: '新增时间',
            sortFiled: 'createTime'
        },
        {
            name: '员工',
            sortFiled: 'employee'
        },
        {
            name: '代理商名称',
            sortFiled: ''
        },
        {
            name: '院校',
            sortFiled: ''
        },
        {
            name: '二级学院',
            sortFiled: ''
        },
        {
            name: '是否圈地',
            sortFiled: ''
        },
        {
            name: '报备项目',
            sortFiled: ''
        },
        {
            name: '计划成交金(元)',
            sortFiled: ''
        },
        {
            name: '计划招标时间',
            sortFiled: ''
        },
        {
            name: '项目级别',
            sortFiled: ''
        },
        {
            name: '联系人/代理商销售',
            sortFiled: ''
        },
        {
            name: '项目决策人',
            sortFiled: ''
        },
        {
            name: '跟进1',
            sortFiled: ''
        },
        {
            name: '跟进2',
            sortFiled: ''
        },
        {
            name: '跟进3',
            sortFiled: ''
        },
    ];

    var tableBody = [];
    for (var i = 0; i < resList.length; i++) {
        var tmp = {};
        tmp.createTime = {
            value: resList[i].createTime,
        };
        tmp.employee = {
            value: resList[i].employee,
        };
        tmp.agent = {
            value: resList[i].agent,
            link: '/customer/agentInfo?agentId=' + resList[i].agentId
        };
        tmp.college = {
            value: resList[i].college,
            link: '/customer/customerInfo?customerId=' + resList[i].collegeId
        };
        tmp.secondCollege = {
            value: resList[i].secondCollege,
        };
        tmp.whetherEnclosure = {
            value: resList[i].whetherEnclosure,
        };
        tmp.project = {
            value: resList[i].project,
            link: '/opportunity/opportunityDl?oppId=' + resList[i].projectId
        };
        tmp.money = {
            value: resList[i].money,
        };
        tmp.inviteTime = {
            value: resList[i].inviteTime,
        };
        tmp.projectLevel = {
            value: resList[i].projectLevel,
        };
        tmp.contact = {
            value: resList[i].contact,
            link: '/customer/agentLinkInfo?linkUserId=' + resList[i].contactId
        };
        tmp.decisionMaker = {
            value: resList[i].decisionMaker,
            link: '/customer/contactsInfo?contactsId=' + resList[i].decisionMakerId
        };
        tmp.follow1 = {
            value: resList[i].follow1,
        };
        tmp.follow2 = {
            value: resList[i].follow2,
        };
        tmp.follow3 = {
            value: resList[i].follow3,
        };
        tableBody.push(tmp);
    }
    dataList.tableHead = tableHead;
    dataList.tableBody = tableBody;
    return dataList;
}

//构建客户详情列表
function buildCustomerDataList(resList) {
    var dataList = {};
    var tableHead = [
        {
            name: '新增时间',
            sortFiled: 'createTime'
        },
        {
            name: '员工',
            sortFiled: 'employeeName'
        },
        {
            name: '院校',
            sortFiled: ''
        },
        {
            name: '二级学院',
            sortFiled: ''
        },
        {
            name: '联系人',
            sortFiled: ''
        },
        {
            name: '职位',
            sortFiled: 'position'
        },
        {
            name: '性别',
            sortFiled: ''
        },
        {
            name: '电话',
            sortFiled: ''
        },
        {
            name: '座机',
            sortFiled: ''
        },
        {
            name: '微信',
            sortFiled: ''
        },
        {
            name: 'QQ',
            sortFiled: ''
        },
        {
            name: '邮箱',
            sortFiled: ''
        },
    ];

    var tableBody = [];
    for (var i = 0; i < resList.length; i++) {
        var tmp = {};
        tmp.createTime = {
            value: resList[i].createTime,
        };
        tmp.employeeName = {
            value: resList[i].employeeName,
        };
        tmp.school = {
            value: resList[i].school,
            link: resList[i].url
        };
        tmp.subDept = {
            value: resList[i].subDept,
        };
        tmp.linker = {
            value: resList[i].linker,
            link: '/customer/agentLinkInfo?linkUserId=' + resList[i].linkerId
        };
        tmp.position = {
            value: resList[i].position,
        };
        tmp.gender = {
            value: resList[i].gender,
        };
        tmp.mobile = {
            value: resList[i].mobile,
        };
        tmp.phone = {
            value: resList[i].phone,
        };
        tmp.weiChat = {
            value: resList[i].weiChat,
        };
        tmp.QQ = {
            value: resList[i].QQ,
        };
        tmp.mail = {
            value: resList[i].mail,
        };
        tableBody.push(tmp);
    }
    dataList.tableHead = tableHead;
    dataList.tableBody = tableBody;
    return dataList;
}

//构建代理商详情列表
function buildAgentDataList(resList) {
    var dataList = {};
    var tableHead = [
        {
            name: '新增时间',
            sortFiled: 'createTime'
        },
        {
            name: '员工',
            sortFiled: 'employee'
        },
        {
            name: '客户来源',
            sortFiled: ''
        },
        {
            name: '代理商名称',
            sortFiled: ''
        },
        {
            name: '客户等级',
            sortFiled: ''
        },
        {
            name: '主要销售成员',
            sortFiled: ''
        },
        {
            name: '背景',
            sortFiled: ''
        },
        {
            name: '本月联系',
            sortFiled: ''
        },
        {
            name: '上月联系',
            sortFiled: ''
        },
        {
            name: '上上月联系',
            sortFiled: ''
        },
    ];

    var tableBody = [];
    for (var i = 0; i < resList.length; i++) {
        var tmp = {};
        tmp.createTime = {
            value: resList[i].createTime,
        };
        tmp.employee = {
            value: resList[i].employee,
        };
        tmp.customerFrom = {
            value: resList[i].customerFrom,
        };
        tmp.agentName = {
            value: resList[i].agentName,
            link: '/customer/agentInfo?agentId=' + resList[i].agentId
        };
        tmp.customerLevel = {
            value: resList[i].customerLevel
        };
        tmp.mainSaleMember = {
            value: resList[i].mainSaleMember,
        };
        tmp.background = {
            value: resList[i].background,
        };
        tmp.thisLinkTimes = {
            value: resList[i].thisLinkTimes,
        };
        tmp.lastLinkTimes = {
            value: resList[i].lastLinkTimes,
        };
        tmp.lastLastLinkTimes = {
            value: resList[i].lastLastLinkTimes,
        };
        tableBody.push(tmp);
    }
    dataList.tableHead = tableHead;
    dataList.tableBody = tableBody;
    return dataList;
}

//构建院校拜访详情列表
function buildVisitDataList(resList) {
    var dataList = {};
    var tableHead = [
        {
            name: '拜访接待日期',
            sortFiled: 'receiveDate'
        },
        {
            name: '员工',
            sortFiled: 'employee'
        },
        {
            name: '拜访类型',
            sortFiled: ''
        },
        {
            name: '拜访深浅',
            sortFiled: ''
        },
        {
            name: '外出',
            sortFiled: ''
        },
        {
            name: '院校',
            sortFiled: ''
        },
        {
            name: '二级学院',
            sortFiled: ''
        },
        {
            name: '联系人',
            sortFiled: ''
        },
        {
            name: '联系方式',
            sortFiled: ''
        },
        {
            name: '拜访事由',
            sortFiled: ''
        },
        {
            name: '拜访过程及结果',
            sortFiled: ''
        },
    ];

    var tableBody = [];
    for (var i = 0; i < resList.length; i++) {
        var tmp = {};
        tmp.receiveDate = {
            value: resList[i].receiveDate,
        };
        tmp.employee = {
            value: resList[i].employee,
        };
        tmp.visitType = {
            value: resList[i].visitType,
        };
        tmp.visitDepth = {
            value: resList[i].visitDepth,
        };
        tmp.out = {
            value: resList[i].out
        };
        tmp.college = {
            value: resList[i].college,
            link: '/customer/customerInfo?customerId=' + resList[i].collegeId
        };
        tmp.subDept = {
            value: resList[i].subDept,
        };
        tmp.contact = {
            value: resList[i].contact,
            link: '/customer/agentLinkInfo?linkUserId=' + resList[i].contactId
        };
        tmp.mobile = {
            value: resList[i].mobile,
        };
        tmp.visitReason = {
            value: resList[i].visitReason,
        };
        tmp.visitProcessAndResult = {
            value: resList[i].visitProcessAndResult,
        };
        tableBody.push(tmp);
    }
    dataList.tableHead = tableHead;
    dataList.tableBody = tableBody;
    return dataList;
}