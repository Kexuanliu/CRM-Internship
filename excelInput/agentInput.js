jQuery(document).ready(function () {
    var type = getQueryString("type");

    var agentInputVue = new Vue({
        el: '#agentInputVue',
        data: {
            editTitle: '代理商',
            uploadUrl: "/customer/loadAgentExcel",
            downLoadTmpUrl: "/excelInput/代理商导入表格模板.xlsx",
            showType: "showMain",
            errMsg: "",
            showErrMsg: false,
            res: {
                successCount: 0,
                failCount: 0,
                finishTime: "",
                errorList: [],
                show: true
            },
            histortyList: [],
            type: 1
        },
        methods: {
            'backToList': function () {
                history.back();
            },
            'backToMainPage': function () {
                this.showType = "showMain";
            },
            'jumpToHistory': function () {
                this.showType = "showHistory";
                $.ajax({
                    url: "/customer/queryImportHistory",
                    type: "get",
                    data: {'type': this.type},
                    success: function (obj) {
                        var histortyList = [];
                        for (var i = 0; i < obj.historyList.length; i++) {
                            var item = obj.historyList[i];
                            var result = [];
                            for (var j = 0; j < item.excelImportDetailList.length; j++) {
                                var tmp = item.excelImportDetailList[j];
                                result.push(tmp.showInfo);
                            }
                            histortyList.push({
                                time: item.createTime,
                                res: result
                            });
                            agentInputVue.histortyList = histortyList;
                        }
                    }
                })
            },
            'downloadTmp': function () {
                window.open(this.downLoadTmpUrl);
            },
            'alertError': function (msg) {
                this.errMsg = msg;
                this.showErrMsg = true;
            },
            'clearErrorList': function () {
                if (confirm("确定清除此次错误记录吗?")) {
                    window.localStorage.setItem("importRes" + type, null);
                    this.res = {
                        successCount: 0,
                        failCount: 0,
                        finishTime: "",
                        errorList: [],
                        show: false
                    }
                    window.location.reload();
                }
            },
            'translateDate':function (date) {
                return date.split(' ')[0];
            },
            'uploadExcel': function () {
                var formData = new FormData();
                var files = $('#excelFile').prop('files');
                if (files.length == 1) {
                    var name = files[0].name.toLowerCase();
                    var arr = name.split(".");
                    if (arr.length > 2 || !(arr[1] == "xls" || arr[1] == "xlsx")) {
                        this.alertError("请选择正确的excel文件");
                    }
                    formData.append("excelName", name);
                    formData.append("file", files[0]);
                    var $loadingToast = $('#loadingToast');
                    if ($loadingToast.css('display') != 'none') return;
                    $loadingToast.fadeIn(100);
                    $.ajax({
                        url: this.uploadUrl,
                        type: "post",
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (obj) {
                            var res = obj.res;
                            agentInputVue.res.errorList = [];
                            if (res.failCount > 0) {
                                if (res.errorMsgsList) {
                                    for (var j = 0; j < res.errorMsgsList.length; j++) {
                                        var item = res.errorMsgsList[j];
                                        agentInputVue.res.errorList.push({
                                            name: item.showInfo,
                                            row: item.rows,
                                            errorMsg: agentInputVue.translateErrorCode(item.errorCode, item.msg)
                                        });
                                    }
                                }
                            }
                            agentInputVue.res.finishTime = obj.createTime;
                            agentInputVue.res.successCount = res.successCount;
                            agentInputVue.res.failCount = res.failCount;
                            agentInputVue.res.show = true;
                            var str = JSON.stringify(agentInputVue.res);
                            window.localStorage.setItem("importRes" + type, str);
                            window.location.reload();
                        }
                    });
                } else {
                    this.alertError("请选择一个excel文件")
                }
            },
            'chooseFile': function () {
                $("#excelFile").click();
            },
            'translateErrorCode': function (input, msg) {
                switch (input) {
                    case 'SAMEAGENT':
                        return "导入出错，错误原因：代理商名称在系统中已存在，如果您未导入过此代理商请联系管理员查看重名代理商。";
                    case 'USERERROR':
                        return "导入出错，错误原因：员工姓名和当前账号姓名不相符。";
                    case 'FIELDERROR':
                        return "导入出错，错误原因：" + msg + " 要求不符合。";
                    case 'SAMEOPP':
                        return "导入出错，错误原因：商机名称在系统中已存在，如果您未导入过此商机请联系管理员查看重名商机。";
                    case 'NOTEXIST':
                        return "导入出错，错误原因：" + msg + " 未创建,请先在系统中创建缺少的信息。";
                    case 'SAMECONTACT':
                        return "导入出错，错误原因：" + msg + "该信息已创建";
                    default:
                        return "未知错误";
                }
            },
        },
        mounted() {
            switch (type) {
                case '1':
                    this.editTitle = '代理商';
                    this.uploadUrl = "/customer/loadAgentExcel";
                    this.downLoadTmpUrl = "/excelInput/代理商导入表格模板.xlsx";
                    this.type = 1;
                    break;
                case '2':
                    this.editTitle = '院校';
                    this.uploadUrl = "/customer/loadSchoolExcel";
                    this.downLoadTmpUrl = "/excelInput/院校导入表格模板.xlsx";
                    this.type = 2;
                    break;
                case '3':
                    this.editTitle = '商机';
                    this.uploadUrl = "/opportunity/loadOppExcel";
                    this.downLoadTmpUrl = "/excelInput/商机导入表格模板.xlsx";
                    this.type = 3;
                    break;
                case '4':
                    this.editTitle = '代理商拜访';
                    this.uploadUrl = "/journal/loadJournalExcel";
                    this.downLoadTmpUrl = "/excelInput/拜访记录表格模板.xlsx";
                    this.type = 4;
                    break;
                default:
                    this.editTitle = '代理商';
                    this.uploadUrl = "/customer/loadAgentExcel";
                    this.downLoadTmpUrl = "/excelInput/代理商导入表格模板.xlsx";
                    this.type = 1;
                    break;
            }
            var str = window.localStorage.getItem("importRes" + type);
            if (str && str != "undefined" && str != "null") {
                var obj = JSON.parse(str);
                this.res = obj;
            } else {
                this.res = {
                    successCount: 0,
                    failCount: 0,
                    finishTime: "",
                    errorList: [],
                    show: false
                }
            }
        }
    });
});