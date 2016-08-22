package com.github.redhatqe.rhsm.testpolarize;


import com.github.redhatqe.polarize.metadata.Requirement;
import com.github.redhatqe.polarize.metadata.TestDefinition;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * A dummy example showing how to use the annotations.  Since these are repeating annotations, that means that the
 * java source code must be compiled at Java source of 8
 *
 * Created by stoner on 7/12/16.
 */
@Requirement(project="RHEL6", author="Sean Toner",
             description="Class level Requirement for RHEL 6.  All test methods will inherit from a class annotated " +
                     "with @Requirement.  If a test method's @Polarion annotation has a non-empty reqs field, any " +
                     "@Requirements there will override the class Requirement for that method")
@Requirement(project="RedHatEnterpriseLinux7", author="CI User",
             description="Class level Requirement for RHEL7")
public class TestReq {

    @TestDefinition(projectID="RHEL6",
              description="TestCase for a dummy test method",
              xmlDesc="",
              reqs={})
    @TestDefinition(author="Sean Toner",                 // required
              projectID="RedHatEnterpriseLinux7",  // required
              testCaseID="RHEL7-56743",            // if empty or null, make request to WorkItem Importer tool
              caseimportance="medium",             // defaults to high  if not given
              caseposneg="negative",               // defaults to positive if not given
              caselevel="component",               // defaults to component if not given
              testtype="non_functional",           // defaults to functional if not given
              reqs={@Requirement(id="",            // if empty, look for class Requirement.  If no class requirement
                                                   // look for xmlDesc.  If none, that means request one.
                                 project="RedHatEnterpriseLinux7",
                                 description="This description will override class level",
                                 xmlDesc="/tmp/path/to/xml/description/testUpgradeNegative.xml",
                                 feature="/tmp/path/to/a/gherkin/file/requirements.feature")},
              setup="Description of any preconditions that must be established for test case to run",
              teardown="The methods to clean up after a test method")
    @Test(groups={"simple"},
          description="Test for reporter code",
          dataProvider="simpleProvider")
    public void testUpgradeNegative(String name, int age) {
        AssertJUnit.assertEquals(age, 44);
        Assert.assertTrue(name.equals("Sean"));
    }

    @DataProvider(name="simpleProvider")
    public Object[][] dataDriver() {
        Object[][] table = new Object[2][2];
        List<Object> row = new ArrayList<>();

        row.add("Sean");
        row.add(44);
        table[0] = row.toArray();

        row = new ArrayList<>();
        row.add("Toner");
        row.add(0);
        table[1] = row.toArray();
        return table;
    }
}
