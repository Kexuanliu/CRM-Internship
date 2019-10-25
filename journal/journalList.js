var TYPE_NAMES = {
    'DAILY': '日报',
    'WEEKLY': '周报',
    'MONTHLY': '月报'
};

jQuery(document).ready(function () {
    var journalListVue = new Vue({
        el: '#journalVue',
        data: {
            showPage: 'journalList',
            showDetail: false,
            showAddJournalDialog: false,
            showAddJournalDialog2: false,
            journalList: '',
            curJournal: {
                user: {}
            },
            isAdmin: false,
            customers: [],
            projects: [],
            subMemberList: [],
            sendersId: [],
            journalLists: [],
            delay: 30,
            miss: 100,
            sendersName: [],
            tempSenders: [],
            followJournal: [],
            journalExcel: [],
            showRead: true,
            journalType: "",
            client: "",
            repairDate: [],
            project: "",
            startTime: "",
            manager: '',
            endTime: "",
            isRead: "",
            tip: '',
            errMsg: "",
            repairC: '0',
            loseC: '0',
            totalC: '0',
            showErrMsg: false,
            searchData: {pageNo: 1},
            pageSize: 20,
            hasMore: true,
            // repairTs: '',
        },
        methods: {
            setRepaireTs(){
                this.repaireTs = new Date();
            },
            'searchList': function () {
                this.hasMore = false;
                var data = this.searchData;
                data.pageSize = this.pageSize;
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                $loadingToast.fadeIn(100);
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/list',
                    data: data,
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.hasMore = true;
                        for (var i in result.journalList) {
                            result.journalList[i].newComment = undefined;
                            if (!result.journalList[i].comments) {
                                result.journalList[i].comments = [];
                            }
                        }
                        thisVue.$set(thisVue, 'journalList', thisVue.journalList.concat(result.journalList));
                        thisVue.showPage = 'journalList';
                        $loadingToast.fadeOut(100);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'searchRepair': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/repair',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'repairDate', result.repairDate);
                    thisVue.$set(thisVue, 'isAdmin', result.isAdmin);
                });
            },
            'showOut': function (input, outType) {
                var res = false;
                if (input) {
                    res = true;
                    if (input == 'PHONE' || input == 'OTHER' || outType == 0 || outType == undefined) {
                        res = false;
                    }
                }
                return res;
            },
            'translateOutType': function (input) {
                if (input == 1) {
                    return "市内"
                } else if (input == 2) {
                    return "市外"
                } else {
                    return "";
                }
            },
            'change': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/money',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'delay', result.delay);
                    thisVue.$set(thisVue, 'miss', result.miss);
                    thisVue.showPage = 'money';
                });
            },
            'changemoney': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/changemoney',
                    data: {
                        delay: thisVue.delay,
                        miss: thisVue.miss
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    alert("修改成功");
                });
            },
            'managerJournal': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/manager',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'repairC', result.repairC);
                    thisVue.$set(thisVue, 'loseC', result.loseC);
                    thisVue.$set(thisVue, 'totalC', result.totalC);
                    thisVue.$set(thisVue, 'manager', result.manager);
                    thisVue.$set(thisVue, 'delay', result.delay);
                    thisVue.$set(thisVue, 'miss', result.miss);
                    thisVue.showPage = 'showManager';
                });
            },
            'follow': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: "post",
                    url: '/journal/follow',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (!result.successFlg) {
                        thisVue.showErrorMsg(result.errMsg);
                        return;
                    }
                    thisVue.$set(thisVue, 'followJournal', result.followJournal);
                    thisVue.$set(thisVue, 'journalExcel', result.journalExcel);
                    thisVue.$set(thisVue, 'delay', result.delay);
                    thisVue.$set(thisVue, 'miss', result.miss);
                    thisVue.showPage = 'showFollow';
                });
            },
            'CreateExcel': function (index) {
                window.location = "/journal/info?index=" + index;
            },
            'repairJournal': function (date) {
                window.location = "/journal/edit?repairDate=" + date + "&type=DAILY";
            },
            'searchAll': function () {
                this.searchData = {
                    pageNo: 1
                };
                this.journalList = [];
                this.searchList();
            },
            'searchUnRead': function () {
                this.searchData = {
                    pageNo: 1,
                    isRead: 0
                };
                this.searchList();
            },
            'searchMine': function () {
                this.searchData = {
                    pageNo: 1,
                    isMine: 1
                };
                this.journalList = [];
                this.searchList();
            },
            'searchDay': function () {
                this.searchData = {
                    pageNo: 1,
                    journalType: 'DAILY'
                };
                this.journalList = [];
                this.searchList();

            },
            'searchWeek': function () {
                this.searchData = {
                    pageNo: 1,
                    journalType: 'WEEKLY'
                };
                this.journalList = [];
                this.searchList();
            },
            'searchMonth': function () {
                this.searchData = {
                    pageNo: 1,
                    journalType: 'MONTHLY'
                };
                this.journalList = [];
                this.searchList();
            },
            'searchFilter': function () {
                var senderIds = "";
                var clientId = '', clientName = '',
                    projectId = '', projectName = '';
                for (var i = 0; i < this.sendersId.length; i++) {
                    senderIds += this.sendersId[i] + ',';
                }
                if (this.client != '') {
                    var c = this.client.split(',');
                    clientId = c[0];
                    clientName = c[1];
                }
                if (this.project != '') {
                    var p = this.project.split(',');
                    projectId = p[0];
                    projectName = p[1];
                }
                this.searchData = {
                    journalType: this.journalType,
                    customer: clientId,
                    project: projectId,
                    startDate: this.startTime,
                    senderIds: senderIds,
                    endDate: this.endTime,
                    pageNo: 1
                };
                this.tip = this.mergeTip(clientName, projectName);
                $('#resultTip').show();
                this.journalList = [];
                this.searchList();
            },
            'mergeTip': function (clientName, projectName) {
                var mergeTip = '';
                for (var i = 0; i < this.sendersName.length; i++) {
                    mergeTip += this.sendersName[i] + ',';
                }
                if (this.startTime != '' && this.endTime != '') {
                    mergeTip += this.startTime + '到' + this.endTime;
                    mergeTip += clientName != '' ? '与' + clientName : '';
                } else {
                    mergeTip += this.startTime + this.endTime + clientName;
                }
                mergeTip += projectName;
                return mergeTip + ((TYPE_NAMES[this.journalType] != null) ? '的' + TYPE_NAMES[this.journalType] : '');
            },
            'addSenders': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/journal/subMemberList',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'subMemberList', result.subMemberList);
                    thisVue.showPage = 'selSender';
                });
            },
            'backToFilter': function () {
                this.sendersId = [];
                this.sendersName = [];
                this.tempSenders = [];
                this.showPage = 'filterDiv';
            },
            'finish': function () {
                this.sendersId = [];
                this.sendersName = [];
                for (var i = 0; i < this.tempSenders.length; i++) {
                    var str = this.tempSenders[i].split(',');
                    this.sendersId[i] = str[0];
                    this.sendersName[i] = str[1];
                }
                this.showPage = 'filterDiv';
            },
            'loadDetail': function (journal) {
                if (journal.isMine && journal.isToday) {
                    window.location = "/journal/edit?journalId=" + journal.journalId + "&type=DAILY";
                    return;
                }
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/userJournal',
                    data: {
                        journalId: journal.journalId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'journalLists', result.journalLists);
                        thisVue.curJournal = journal;
                        thisVue.showDetail = true;
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'backToList': function () {
                this.showPage = 'journalList';
                this.showDetail = false;
                this.cancelAddJournalEvent();
            },
            'journalName': function (journal) {
                var prefixName = journal.user.realName;
                if (journal.isMine) {
                    prefixName = '我';
                }
                return prefixName + '的' + TYPE_NAMES[journal.type];
            },
            'journalName2': function (journal) {
                var prefixName = journal.user.realName;
                if (journal.isMine) {
                    prefixName = '我';
                }
                return prefixName + '的' + TYPE_NAMES[journal.type] + '修改记录';
            },
            'translateVisitType':function (type) {
               return translateVisitType(type);
            },
            'translateContactType':function (contactType) {
                if (contactType === 0) {
                    return "--无沟通";
                } else if (contactType === 1) {
                    return "--小于等于10分钟";
                } else if (contactType === 2) {
                    return "--大于10分钟";
                }else{
                    return "";
                }
            },

            'clickAddJournalButton': function () {
                this.searchRepair();
                this.showAddJournalDialog = true;
            },
            'clickAddJournalButton2': function () {
                this.searchRepair();
                this.showAddJournalDialog2 = true;
            },
            'cancelAddJournalEvent': function () {
                this.showAddJournalDialog = false;
                this.showAddJournalDialog2 = false;
            },
            'toFilter': function () {
                this.journalType = '';
                this.client = '';
                this.project = '';
                this.startTime = this.endTime = '';
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/journal/customerAndProjects',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'customers', result.customers);
                        thisVue.$set(thisVue, 'projects', result.opportunities);
                        // console.log(result);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
                this.showPage = 'filterDiv';
            },
            'quit': function () {
                this.showPage = 'journalList';
            },
            'patchAction': function (journalId) {
                jQuery("#patchButtonDiv_" + journalId).css("display", "none");
                jQuery("#patchContent_" + journalId).val("");
                jQuery("#patchDiv_" + journalId).css("display", "block");
            },
            'patchSubmit': function (journalId) {
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/action/journalAttachment',
                    data: {
                        journalId: journalId,
                        content: jQuery("#patchContent_" + journalId).val()
                    },
                    dataType: 'json',
                    cache: false,
                    success: function () {
                        location.reload(true);
                    }
                });
                jQuery("#patchButtonDiv_" + journalId).css("display", "block");
                jQuery("#patchDiv_" + journalId).css("display", "none");
            },
            'patchCancelSubmit': function (journalId) {
                jQuery("#patchButtonDiv_" + journalId).css("display", "block");
                jQuery("#patchDiv_" + journalId).css("display", "none");
            },
            'convertVisitType': function (ty) {
                var str;
                if (ty === 'VISIT') {
                    str = '市外拜访';
                } else if (ty === 'VISIT_INSIDE') {
                    str = "市内拜访";
                } else {
                    str = '电话';
                }
                return str;
            },
            showErrorMsg: function (errMsg) {
                this.errMsg = errMsg;
                this.showErrMsg = true;
            },
            'loadMore': function () {
                this.searchData.pageNo = this.searchData.pageNo + 1;
                this.searchList();
            }
        },
        components: {
            'comment-div': {
                template: '#comment-div',
                props: ['journal'],
                data: function () {
                    this.journal.newComment = this.journal.newComment || undefined;
                    return {};
                },
                methods: {
                    addComment: function (journal, event) {
                        if (journal.newComment === undefined || journal.newComment === '') {
                            journalListVue.showErrorMsg("请输入评论");
                            return;
                        }
                        jQuery(event.target).attr("disabled", true);
                        jQuery.ajax({
                            type: 'post',
                            url: "/journal/comment/addComment",
                            data: {
                                comment: journal.newComment,
                                journalId: journal.journalId
                            },
                            cache: false,
                            dataType: 'json'
                        }).done(function (result) {
                            jQuery(event.target).removeAttr("disabled");
                            if (result.successFlg) {
                                journal.comments.push(result.comment);
                                journal.newComment = undefined;
                            } else {
                                journalListVue.showErrorMsg(result.errMsg);
                            }
                        });
                    }
                }
            },
            'visit-comment-div': {
                template: '#visit-comment-div',
                props: ['visitRecord'],
                data: function () {
                    this.visitRecord.newComment = this.visitRecord.newComment || undefined;
                    return {};
                },
                methods: {
                    addVisitComment: function (visitRecord, event) {
                        if (visitRecord.newComment === undefined || visitRecord.newComment === '') {
                            journalListVue.showErrorMsg("请输入评论");
                            return;
                        }
                        jQuery(event.target).attr("disabled", true);
                        jQuery.ajax({
                            type: 'post',
                            url: "/journal/visitlogcomment/addVisitComment",
                            data: {
                                comment: visitRecord.newComment,
                                visitLogId: visitRecord.visitId
                            },
                            cache: false,
                            dataType: 'json'
                        }).done(function (result) {
                            jQuery(event.target).removeAttr("disabled");
                            if (result.successFlg) {
                                visitRecord.comments.push(result.comment);
                                visitRecord.newComment = undefined;
                            } else {
                                journalListVue.showErrorMsg(result.errMsg);
                            }
                        });
                    }
                }
            }
        }
    });
    journalListVue.searchAll();
});

