/**
 * Created by Administrator on 2018/7/17.
 */

jQuery(document).ready(function () {

    var organizationVue = new Vue({
        el: '#organizationVue',
        data:{
            customerName: '',
            customerId:'',
            companyId:'',
            visitCount:0,
            a:0,
            aRate:'0.00',
            companyData:'',
            personCount:0
        },
        methods: {
            'init':function (customerId, customerName ,companyId) {
                this.customerId = customerId;
                this.customerName = customerName;
                this.companyId=companyId;
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/company/oppData',
                    data:
                        {
                            customerId:this.customerId,
                            companyId:this.companyId
                        },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    thisVue.$set(thisVue, 'companyData', result.companyData);
                    thisVue.$set(thisVue, 'visitCount', result.visitCount);
                    thisVue.$set(thisVue, 'a', result.a);
                    thisVue.$set(thisVue, 'personCount', result.personCount);
                    thisVue.$set(thisVue, 'aRate', result.aRate);
                });
            },
            'toCustomerList':function () {
                window.location='/customer/customerList';
            },
            'toCustomerInfo':function () {
                window.location='/customer/customerInfo?customerId='+this.customerId;
            },

        }
    });

    var customerId = $("#customerId").val();
    var customerName = $("#customerName").val();
    var companyId=$("#companyId").val();
    organizationVue.init(customerId,customerName,companyId);
});
