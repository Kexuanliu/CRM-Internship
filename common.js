var stageListData = [
    {
        item: "A",
        value: "A 获取客户信息"
    },
    {
        item: "B1",
        value: "B1 方案撰写"
    },
    {
        item: "B2",
        value: "B2 方案申报及落实资金"
    },
    {
        item: "B3",
        value: "B3 项目论证"
    },
    {
        item: "B4",
        value: "B4 写招标参数"
    },
    {
        item: "C",
        value: "C 挂网招标"
    },
    {
        item: "D",
        value: "D 中标签合同"
    },
    {
        item: "F",
        value: "输单"
    },
]


function translateOppStage(input) {
    var res = "";
    switch (input) {
        case "A":
            res = "获取客户信息";
            break;
        case "B1":
            res = "方案撰写";
            break;
        case "B2":
            res = "方案申报及落实资金";
            break;
        case "B3":
            res = "项目论证";
            break;
        case "B4":
            res = "写招标参数";
            break;
        case "C":
            res = "挂网招标";
            break;
        case "D":
            res = "中标签合同";
            break;
        case "F":
            res = "输单";
            break;
    }
    return res;
}

function convertStageNum(input) {
    switch (input) {
        case 'A':
            return 0;
        case 'B1':
            return 1;
        case 'B2':
            return 2;
        case 'B3':
            return 3;
        case 'B4':
            return 4;
        case 'C':
            return 5;
        case 'D':
            return 6;
        case 'F':
            return 7;
        default :
            return -1;
    }
}


function translateOppStageImg(input) {
    switch (input) {
        case "A":
            return '/images/opportunity/AStage.svg';
            break;
        case "B1":
            return '/images/opportunity/B1Stage.svg';
            break;
        case "B2":
            return '/images/opportunity/B2Stage.svg';
            break;
        case "B3":
            return '/images/opportunity/B3Stage.svg';
            break;
        case "B4":
            return '/images/opportunity/B4Stage.svg';
            break;
        case "C":
            return '/images/opportunity/CStage.svg';
        case "D":
            return '/images/opportunity/DStage.svg';
        case "F":
            return '/images/opportunity/loseOrder.svg';
    }
}

function translateVisitType(visitType) {
    switch (visitType) {
        case "VISIT":
            return "拜访";
        case "VISIT_INSIDE":
            return "市内拜访";
        case "OFFLINE":
            return "线下拜访";
        case "PHONE":
            return "电话联系";
        case "NORMAL_VISIT":
            return "普通拜访";
        case "TRAN_VISIT":
            return "培训拜访";
        case "ACCOMPANY_VISIT":
            return "陪同拜访";
        case "COMPANY_RECEPTION":
            return "公司接待";
        case "OTHER":
            return "其他";
        default :
            return "";
    }
}

function getQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};