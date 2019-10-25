/**
 * Created by Administrator on 2018/7/17.
 */

$(document).ready(function () {

    var searchCustInfoVue = new Vue({
        el: '#customerVue',
        data: {
            searchCustomer: true,
            showAddJournalDialog: false,
            searchRes: false,
            customers: true,
            titleBar: true,
            blank: false,
            searchWord: '',
            errMsg: undefined,
            customerList: [],
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
                    url: '/customer/queryCustomer',
                    data: {
                        searchWord: thisVue.searchWord
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        if (thisVue.searchWord.length > 0) {
                            thisVue.$set(thisVue, 'customerList', result.customerList);
                            thisVue.customers = false;
                            thisVue.searchRes = true;
                        } else {
                            thisVue.$set(thisVue, 'customerList', result.customerList);
                            thisVue.$set(thisVue, 'myCustomers', result.myCustomers);
                            thisVue.$set(thisVue, 'commonCustomers', result.commonCustomers);
                        }
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
                this.customers = true;
                this.searchRes = false;
                this.titleBar = true;
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
                    window.location.href = "/customer/customerInfo?customerId=" + customer.customerId;
                }, 500);
            },
            'goAgentList': function () {
                window.location.href = '/customer/agentList';
            },
            'addCustomer':function () {
                this.showAddJournalDialog = true;
            },
            'cancelAddCustomerEvent': function () {
                this.showAddJournalDialog = false;
            },
            'transferTime': function (input) {
                if (input) {
                    return input.split(' ')[0];
                } else {
                    return '';
                }

            },
        },
        watch: {
            'searchWord': function () {
                var thisVue = this;
                this.showResult();
                if (thisVue.name == '') {
                    thisVue.$set(thisVue, 'companyList', '');
                    thisVue.$set(thisVue, 'customerList', '');
                    thisVue.$set(thisVue, 'myCustomers', '');
                    thisVue.$set(thisVue, 'commonCustomers', '');
                    thisVue.loading = false;
                }
            }
        }
    });
    searchCustInfoVue.showResult();
});
