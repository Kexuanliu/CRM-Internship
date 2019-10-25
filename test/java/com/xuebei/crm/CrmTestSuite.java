package com.xuebei.crm;

import com.xuebei.crm.company.CompanyControllerTest;
import com.xuebei.crm.customer.CustomerControllerTest;
import com.xuebei.crm.customer.CustomerServiceImplTest;
import com.xuebei.crm.department.DeptServiceImplTest;
import com.xuebei.crm.journal.JournalCommentControllerTest;
import com.xuebei.crm.journal.JournalControllerTest;
import com.xuebei.crm.journal.JournalServiceImplTest;
import com.xuebei.crm.opportunity.OpportunityServiceImplTest;
import com.xuebei.crm.utils.CrmMapBuildUtilsTest;
import com.xuebei.crm.utils.UUIDGeneratorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Rong Weicheng on 2018/8/10.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerControllerTest.class,
        CustomerServiceImplTest.class,
        JournalControllerTest.class,
        JournalServiceImplTest.class,
        OpportunityServiceImplTest.class,
        CompanyControllerTest.class,
        DeptServiceImplTest.class,
        JournalCommentControllerTest.class,
        CrmMapBuildUtilsTest.class,
        UUIDGeneratorTest.class
})
public class CrmTestSuite {
}
