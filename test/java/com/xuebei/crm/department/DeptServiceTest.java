/**
 * @author ZXL
 * @date 2019/7/8 10:14
 */
package com.xuebei.crm.department;

import com.google.gson.Gson;
import com.xuebei.crm.customer.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeptServiceTest {

    @Autowired
    private DeptServiceImpl deptService;

    @Test
    public void testGetLinker(){
        String deptId="52e0c9f4debf4602a40dc1c65b8a6440";
        Department department = deptService.getDepartmentLink(deptId);
        Gson gson=new Gson();
        System.out.println(gson.toJson(department));
    }
}
