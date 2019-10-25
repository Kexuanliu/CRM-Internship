package com.xuebei.crm.department;

import com.google.gson.Gson;
import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.Department;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rong Weicheng
 */
@RunWith(MockitoJUnitRunner.class)
public class DeptServiceImplTest {

    @InjectMocks
    private DeptServiceImpl deptService;

    @Test
    public void testFilterDeptListAll() {
        String keyword = "顶级部门";
        List<Department> departments = mockDeptList();
        boolean rlt = deptService.filterDepartmentList(departments, keyword);
        Assert.assertTrue(rlt);
        Assert.assertEquals(2, departments.get(0).getDepartmentList().size());
        Assert.assertEquals(1, departments.get(0).getContactsList().size());
        Assert.assertEquals(1, departments.get(0).getDepartmentList().get(0).getContactsList().size());
    }

    @Test
    public void testFilterDeptListSub() {
        String keyword = "子部门1";
        List<Department> departments = mockDeptList();
        boolean rlt = deptService.filterDepartmentList(departments, keyword);
        Assert.assertTrue(rlt);
        Assert.assertEquals(1, departments.get(0).getDepartmentList().size());
        Assert.assertEquals(0, departments.get(0).getContactsList().size());
        Assert.assertEquals(1, departments.get(0).getDepartmentList().get(0).getContactsList().size());
    }

    @Test
    public void testFilterDeptListAllSub() {
        String keyword = "子部门";
        List<Department> departments = mockDeptList();
        boolean rlt = deptService.filterDepartmentList(departments, keyword);
        Assert.assertTrue(rlt);
        Assert.assertEquals(2, departments.get(0).getDepartmentList().size());
        Assert.assertEquals(0, departments.get(0).getContactsList().size());
        Assert.assertEquals(1, departments.get(0).getDepartmentList().get(0).getContactsList().size());
    }

    @Test
    public void testFilterDeptListNoSub() {
        String keyword = "顶级联系人";
        List<Department> departments = mockDeptList();
        boolean rlt = deptService.filterDepartmentList(departments, keyword);
        Assert.assertTrue(rlt);
        Assert.assertEquals(0, departments.get(0).getDepartmentList().size());
        Assert.assertEquals(1, departments.get(0).getContactsList().size());
    }

    private List<Department> mockDeptList() {
        List<Department> departments = new ArrayList<>();
        Department department = new Department();
        department.setDeptName("顶级部门1");
        Department subDept1 = new Department();
        subDept1.setDeptName("子部门1");
        Contacts contacts1 = new Contacts();
        contacts1.setRealName("联系人1");
        List<Contacts> contactsList1 = new ArrayList<>(1);
        contactsList1.add(contacts1);
        subDept1.setContactsList(contactsList1);
        Department subDept2 = new Department();
        subDept2.setDeptName("子部门2");
        Contacts contacts2 = new Contacts();
        contacts2.setRealName("联系人2");
        List<Contacts> contactsList2 = new ArrayList<>(1);
        contactsList2.add(contacts2);
        subDept2.setContactsList(contactsList2);
        department.addSubDept(subDept1);
        department.addSubDept(subDept2);

        List<Contacts> topContactsList = new ArrayList<>(1);
        Contacts topContacts = new Contacts();
        topContacts.setRealName("顶级联系人");
        topContactsList.add(topContacts);
        department.setContactsList(topContactsList);
        departments.add(department);
        return departments;
    }




}