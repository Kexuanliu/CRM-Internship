/**
 * Created by Administrator on 2018/7/18.
 */
var color = ["", "#2eff4e", "#2b79ff", "#ff7e24"];
var percent = [0, 80, 75, 90];
var TYPE_NAMES = {
    'DAILY': '日报',
    'WEEKLY': '周报',
    'MONTHLY': '月报'
};
$(document).ready(function () {

    var customerVue = new Vue({
        el: '#customerVue',
        data: {
            companyName: '',
            agentId: '',
            showEditDialog: false,
            companyName: '',
            showPage: '',
            hasMore: true,
            errMsg: '',
            titleBar: 'titleBar ',
            showErrMsg: false,
            showMainBar: true,
            agentLinkUser: [],
            journalList: [],
            pageSize: 10,
            searchData: {pageNo: 1},
            showAddJournalDialog: false,
        },
        methods: {
            'init': function (agentId, companyName) {
                this.agentId = agentId;
                this.companyName = companyName;

                this.hasMore = false;
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                $loadingToast.fadeIn(100);
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: 'queryJournalListByAgentId',
                    data: {agentId: agentId},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'journalList', thisVue.journalList.concat(result.journalList));
                        thisVue.showPage = 'journalList';
                        $loadingToast.fadeOut(100);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });

            },
            'goOpp': function () {
                window.location.href = '/customer/agentRelOpp?agentId=' + this.agentId + "&companyName=" + this.companyName;
            },
            'cancelAddCustomerEvent': function () {
                this.showAddJournalDialog = false;
            },
            'backToInfo': function () {
                window.location =  '/customer/agentList';
            },
            'loadMore': function () {
                this.searchData.pageNo = this.searchData.pageNo + 1;
                this.searchList();
            },
            'journalName': function (journal) {
                var prefixName = journal.user.realName;
                if (journal.isMine) {
                    prefixName = '我';
                }
                return prefixName + '的' + TYPE_NAMES[journal.type];
            },
            'translateVisitType':function (type) {
                switch (type) {
                    case "VISIT": return "拜访";
                    case "VISIT_INSIDE": return "市内拜访";
                    case "OFFLINE": return "拜访";
                    case "PHONE": return "电话联系";
                    case "NORMAL_VISIT": return "普通拜访";
                    case "TRAN_VISIT": return "培训拜访";
                    case "ACCOMPANY_VISIT": return "陪同拜访";
                    case "COMPANY_RECEPTION": return "公司接待";
                    case "OTHER": return "其他";
                    default : return "";
                }
            },
            'translateTime': function (input) {
                return input.split(' ')[0];
            }
        }
    });

    var agentId = $("#agentId").val();
    var companyName = $("#companyName").val();

    customerVue.init(agentId, companyName);

});



