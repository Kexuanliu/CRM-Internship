new Vue({
    el: '#title',
    data: {
        title: "新增A级人员"
    }
})

let arr = ["新增时间", "员工", "院校", "二级学院", "联系人", "职位", "性别", "电话", "座机", "微信", "QQ", "邮箱"];
let firstRow = ["2019-1-2", "刘洋", "浙江大学", "机械学院", "张三", "副主任", "男", "13648765678", "655321", "kj756382", "6352748", "k8573@126.com"];
let rows = [["2019-1-2", "刘洋", "浙江大学", "机械学院", "张三", "副主任", "男", "13648765678", "655321", "kj756382", "6352748", "k8573@126.com"],
    ["2019-2-5", "刘洋", "浙江大学", "机械学院", "李四", "正主任", "女", "15987583932", "564723", "hfj76648932", "88343628", "za85837@126.com"]];
let s = "<table rules='all' border='1' cellpadding='10'>";
s += "<tr style='background-color: #E7ECF4; border-inline-color: #B8C5DE; text-align: center'>";
for (let i = 0; i < arr.length; i++) {
  s += "<th style='height: 32px; font-size: 12px; font-weight: 400; white-space: nowrap'>";
  s += arr[i];
  s += "</th>";
}
let indeOfSchool = arr.indexOf("院校");
let indeOfContact = arr.indexOf("联系人");
for (let k = 0; k < rows.length; k++) {
    s += "</tr>";
    s += "<tr style='border: 1px solid lightgray; text-align: center;'>";
    let currentRow = rows[k];
    for (let i = 0; i < currentRow.length; i++) {
        if (i == indeOfSchool || i == indeOfContact) {
            s += "<td class='blueColor' style='height: 34px; font-size: 12px; opacity: 0.5; border: 0; white-space: nowrap; margin-left: 30px;'>";
        } else {
            s += "<td style='height: 34px; font-size: 12px; opacity: 0.5; border: 0; white-space: nowrap; margin-left: 30px'>";
        }
        s += currentRow[i];
        s += "</td>";
    }
    s += "</tr>";
}
s += "</table>";

new Vue({
    el: '#tableTitle',
    data: {
        getInfo: s
    }
})