/**
 * Created by Administrator on 2018/7/17.
 */

$(document).ready(function () {

    var searchCustInfoVue = new Vue({
        el: '#customerVue',
        data: {
            searchCustomer: true,
            showAddJournalDialog: false,
            searchRes: true,
            titleBar: true,
            blank: false,
            searchWord: '',
            errMsg: undefined,
            crmAgentList: [],
            myCustomers: [],
            commonCustomers: [],
            loading: true,
            errMsg: '',
            showErrMsg: false,
        },
        methods: {
            'showResult': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/customer/queryAgentList',
                    data: {
                        searchWord: thisVue.searchWord
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'crmAgentList', result.crmAgentList);
                        thisVue.loading = false;
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'backToInfo': function () {
                //this.customers = true;
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchInput').focus();
                $('#searchResult').show();
                this.customers = false;
            },
            'filterList': function (customer) {
                return customer.customerName.indexOf(this.searchWord) != -1;
            },
            'hideSearchResult': function () {
                $('#searchResult').hide();
                this.searchWord = "";
            },
            'cancelSearch': function () {
                this.hideSearchResult();
                $('#searchBar').removeClass('weui-search-bar_focusing');
                $('#searchText').show();
            },
            'clear': function () {
                this.searchWord = "";
                $('#searchInput').focus();
            },
            'cancel': function () {
                this.cancelSearch();
                this.searchRes = true;
                $('#searchInput').blur();
            },
            'loadDetail': function (customer) {
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                //var temp = this;
                $loadingToast.fadeIn(100);
                setTimeout(function () {
                    $loadingToast.fadeOut(100);
                    //temp.showPage = 'invite';
                    window.location.href = "/customer/agentInfo?agentId=" + customer.agentId;
                }, 500);
            },
            'goSchoolList': function () {
                window.location.href = '/customer/customerList';
            },
            'addCustomer':function () {
                this.showAddJournalDialog = true;
            },
            'cancelAddCustomerEvent': function () {
                this.showAddJournalDialog = false;
            },
            'translateTime': function (inputString) {
               return inputString.split(' ')[0];
            },
        },
        computed:{
            'filterAgentList': function () {
                var key = this.searchWord;
                if (this.crmAgentList.length > 1) {
                    var agentListTmp = this.crmAgentList;
                    return agentListTmp.filter(function (item) {
                        return (item.companyName.indexOf(key) != -1 || item.createName.indexOf(key) != -1)
                    });
                } else {
                    return this.crmAgentList;
                }

            }
        }
    });
    searchCustInfoVue.showResult();
});
