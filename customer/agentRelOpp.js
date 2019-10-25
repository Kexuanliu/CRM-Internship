/**
 * Created by Administrator on 2018/7/18.
 */

function handleTime(time) {
    if (time < 10)
        return "0" + time;
    else
        return time;
}

var color = ["", "#2eff4e", "#2b79ff", "#ff7e24"];
var percent = [0, 80, 75, 90];
$(document).ready(function () {

    var customerVue = new Vue({
        el: '#customerVue',
        data: {
            companyName: '',
            agentId: '',
            showEditDialog: false,
            companyName: '',
            showPage: '',
            errMsg: '',
            showErrMsg: false,
            showMainBar: true,
            agentLinkUser: [],
            showAddJournalDialog: false,
            opportunityList: '',
            showNull: '',
        },
        methods: {
            'init': function (agentId, companyName) {
                this.agentId = agentId;
                this.companyName = companyName;
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                $loadingToast.fadeIn(100);
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: 'queryOpportunityByAgentId',
                    data: {agentId: agentId},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'opportunityList', result.opportunityList);
                        $loadingToast.fadeOut(100);
                        if (result.opportunityList == null || result.opportunityList == '') {
                            thisVue.showNull = 'null';
                        } else {
                            thisVue.showNull = '';
                        }
                    } else {
                        thisVue.errMsg = result.errMsg;
                        alert(thisVue.errMsg);
                    }
                })

            },
            'goJour': function () {
                window.location.href = '/customer/agentRelJour?agentId=' + this.agentId + "&companyName=" + this.companyName;
            },
            'cancelAddCustomerEvent': function () {
                this.showAddJournalDialog = false;
            },
            'backToInfo': function () {
                window.location = '/customer/agentList';
            },
            'addCustomer':function () {

            },
            'imgSrc': function (data) {
               return translateOppStageImg(data);
            },
            'translateStatus': function (status) {
                return translateOppStage(status);
            },
            'detail': function (oppId) {
                window.location.href = "/opportunity/opportunityDl?oppId=" + oppId;
            },
        }
    });

    var agentId = $("#agentId").val();
    var companyName = $("#companyName").val();

    customerVue.init(agentId, companyName);

});



