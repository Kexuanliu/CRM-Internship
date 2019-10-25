Vue.component('tablecontent', {
    props: {
        titles: {
            type: Array,
        },
        rows: Object
    },
    methods: {
        getTable: function(titles, rows) {
            titles = titles.split(" ");
            rows = rows.split(",");
            let newRows = [];
            for (let i = 0; i < rows.length; i++) {
                let j = rows[i];
                j = j.split(" ");
                newRows.push(j);
            }
            let s = "<table rules='all' border='1' cellpadding='10'>";
            s += "<tr style='background-color: #E7ECF4; border-inline-color: #B8C5DE; text-align: center'>";
            for (let i = 0; i < titles.length; i++) {
                s += "<th style='height: 32px; font-size: 12px; font-weight: 400; white-space: nowrap'>";
                s += titles[i];
                s += "</th>";
            }
            let indeOfSchool = titles.indexOf("院校");
            let indeOfContact = titles.indexOf("联系人");
            for (let k = 0; k < newRows.length; k++) {
                s += "</tr>";
                s += "<tr style='border: 1px solid lightgray; text-align: center;'>";
                let currentRow = newRows[k];
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
            return s;
        }
    },
    template: '<div v-html="getTable(titles, rows)"></div>'
})

new Vue({el: '#infotable'})