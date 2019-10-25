/**
 * @author ZXL
 * @date 2019/6/21 17:34
 */
package com.xuebei.crm.excelImport;

import com.google.gson.Gson;
import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.customer.EnclosureStatusEnum;
import com.xuebei.crm.dataRepair.DataRepairService;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelImportTest {

    @Autowired
    private ExcelImportMapper excelImportMapper;


    @Autowired
    private CustomerMapper customerMapper;


    @Autowired
    private CheckRegionMapper checkRegionMapper;

    @Autowired
    DataRepairService dataRepairService;

    @Test
    public void testInsert() {
        ExcelImportBatchLog excelImportBatchLog = new ExcelImportBatchLog();
        excelImportBatchLog.setBatchLogId(System.currentTimeMillis());
        excelImportBatchLog.setCreateId("123");
        excelImportBatchLog.setImportType(1);
        excelImportMapper.insertExcelBatchLog(excelImportBatchLog);

        ExcelImportDetail excelImportDetail = new ExcelImportDetail();
        excelImportDetail.setBatchLogId(excelImportBatchLog.getBatchLogId());
        excelImportDetail.setCreateId("123");
        excelImportDetail.setExecuteCode("success");
        excelImportDetail.setResult(1);
        excelImportDetail.setShowInfo("河南科技");
        excelImportMapper.insertExcelImportDetail(excelImportDetail);
    }

    @Test
    public void testGetList() {
        List<ExcelImportBatchLog> res = excelImportMapper.getExcelImportBatchLogList("123",1);
        Gson gson = new Gson();
        System.out.println(gson.toJson(res));
    }

    @Test
    public void checkTest(){
        System.out.println(checkRegion("黑龙江省", "黑市", "东北"));
    }

    private boolean checkRegion(String pName, String cName, String rName) {
        Map<String, Character> mm = new HashMap<String, Character>();
        mm.put("华北", '1');
        mm.put("东北", '2');
        mm.put("华东", '3');
        mm.put("华中", '4');
        mm.put("华南", '5');
        mm.put("西南", '6');
        mm.put("西北", '7');

        boolean res = true;
        String pid = checkRegionMapper.getPidByPName(pName);  // given province id
        String pid2 = checkRegionMapper.getPidByCName(cName);  // given province id of given city name

        if (!pid.equals(pid2)) {
            res = false;
        }

        if (pid.charAt(0) != mm.get(rName)) {
            res = false;
        }
        return res;
    }


    @Test
    public void testDepartAdd(){
        Department departmentTmp =new Department();
        departmentTmp.setDeptId(UUIDGenerator.genUUID());
        Customer customer = customerMapper.getCrmCustomerByName("成都大学");
        departmentTmp.setCustomer(customer);
        departmentTmp.setCustomerId("bca600bc76a8476698ca0499bc293497");
        departmentTmp.setCreateId("c1866f0adb2141d29b6d60e75c7a7991");
        departmentTmp.setCreateName("赵欢军");
        departmentTmp.setIsExcelImport(1);
        departmentTmp.setDeptName("测试专业01");
        departmentTmp.setCreateTs(new Date());
        departmentTmp.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
        customerMapper.insertDepartment(departmentTmp);
    }

    @Test
    public void testTruncate(){
        dataRepairService.repairPosition();
    }

    @Test
    public void testRepairData() {
        long begin = System.currentTimeMillis();
        dataRepairService.repairContacts();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }


    @Test
    public void testRepairDeptData() {
        long begin = System.currentTimeMillis();
        dataRepairService.repairDepart();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

//
//    @Test
//    public void testIsNumic() {
//        System.out.println(isNumeric("12.1W"));
//    }



}
