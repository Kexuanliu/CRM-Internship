package com.xuebei.crm.utils;

import com.xuebei.crm.customer.Department;
import com.xuebei.crm.customer.EnclosureStatusEnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rong Weicheng
 */
public class CrmMapBuildUtilsTest {
    @Test
    public void initEnumListMap() throws Exception {
        Map<EnclosureStatusEnum, List<Department>> map = CrmMapBuildUtils.initEnumListMap(EnclosureStatusEnum.class);
        Assert.assertEquals(EnclosureStatusEnum.values().length, map.size());
    }

    @Test
    public void buildEnumListMap() throws Exception {
        List<Department> list = mockDepartmentList();

        Map<EnclosureStatusEnum, List<Department>> map = CrmMapBuildUtils.buildEnumListMap(EnclosureStatusEnum.class,
                list, Department::getEnclosureStatus);

        Assert.assertEquals(EnclosureStatusEnum.values().length, map.size());
        Assert.assertEquals(EnclosureStatusEnum.ENCLOSURE.getOrderValue().intValue(),
                map.get(EnclosureStatusEnum.ENCLOSURE).size());
    }

    private List<Department> mockDepartmentList() {
        List<Department> list = new ArrayList<>();
        for (EnclosureStatusEnum status : EnclosureStatusEnum.values()) {
            for (int i = 0; i <= status.ordinal(); i++) {
                Department department = new Department();
                department.setEnclosureStatus(status);
                list.add(department);
            }
        }
        return list;
    }
}