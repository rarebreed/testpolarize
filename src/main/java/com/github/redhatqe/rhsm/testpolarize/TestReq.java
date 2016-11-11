package com.github.redhatqe.rhsm.testpolarize;


import com.github.redhatqe.polarize.exceptions.ConfigurationError;
import com.github.redhatqe.polarize.metadata.*;
import com.github.redhatqe.polarize.metadata.DefTypes.Project;
import com.github.redhatqe.polarize.metadata.DefTypes.PosNeg;

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
 * **NOTE** The @Requirement, although recognized by the annotation processor are not currently used.  This is because
 * as of yet, there is still no Requirement Importer worked on by the CI Ops team
 *
 * Created by stoner on 7/12/16.
 */
public class TestReq {

    @TestDefinition(projectID=Project.RHEL6,
              description="TestCase for a dummy test method",
              title="A not necessarily unique title",  // defaults to class.methodName
              reqs={})
    @TestDefinition(projectID=Project.PLATTP,      // required
              testCaseID="PLATTP-9520",            // if empty or null, make request to WorkItem Importer tool
              importance=DefTypes.Importance.LOW,  // defaults to high  if not given
              posneg=PosNeg.NEGATIVE,              // defaults to positive if not given
              level= DefTypes.Level.COMPONENT,     // defaults to component if not given
              linkedWorkItems={@LinkedItem(workitemId="PLATTP-10348",         // Required
                      project=Project.PLATTP,                                 // Required. What Project to go under
                      role=DefTypes.Role.VERIFIES)},                          // Required. Role type
              // If testtype is FUNCTIONAL, subtype1 and 2 must be of type EMPTY.
              testtype=@TestType(testtype= DefTypes.TestTypes.NONFUNCTIONAL,  // Defaults to FUNCTIONAL
                                 subtype1= DefTypes.Subtypes.COMPLIANCE,      // Defaults to EMPTY (see note)
                                 subtype2= DefTypes.Subtypes.EMPTY),          // Defaults to EMPTY (see note)
              setup="Description of any preconditions that must be established for test case to run",
              tags="tier1 some_description",
              teardown="The methods to clean up after a test method",
              update=true,
              automation=DefTypes.Automation.AUTOMATED)  // if not given this defaults to AUTOMATED)
    @Test(groups={"simple"},
          description="Test for reporter code",
          dataProvider="simpleProvider")
    public void testUpgradeNegative(String name, int age) {
        AssertJUnit.assertEquals(age, 44);
        Assert.assertTrue(name.equals("Sean"));
    }

    /**
     * Shows an example of duplicated TestDefinition
     *
     * This is a test that uses a DataProvider.  The annotation processor will determine the names of the arguments
     * and use this as the names for the TestStep Parameters.  In the example below, the processor will determine that
     * this method has 2 arguments called "name" and "age".  It will create the XML necessary to include those params.
     *
     * If a method is identical for multiple projects, you can simply set projectID as an array as shown in the example
     * below.  Note that the testCaseID ordering must match.  In other words, the testCaseID at index 0 should match
     * the project in index 0.  When using the arrays, you have to leave the testCaseID as an empty string and can not
     * leave it blank.
     *
     * For example, if the testCaseID exists for Project.RedHatEnterpriseLinux7 but not for Project.RHEL6, you should
     * show it like:
     *
     * projectID={Project.RedHatEnterpriseLinux7, Project.RHEL6},
     * testCaseID={"RHEL7-20458", ""}
     * // testCaseID={"RHEL7-20458"}   <- This is wrong and compiler will fail
     *
     * @param name
     * @param age
     */
    @TestDefinition(projectID={Project.PLATTP, Project.RHEL6})
    @Test(groups={"simple"},
          description="A simple annotation that automatically gets parameter names from method source ",
          dataProvider="simpleProvider")
    public void testUpgrade(String name, int age) {
        Assert.assertTrue(name.equals("Sean") || name.equals("Toner"));
        Assert.assertTrue(age < 100);
    }

    @TestDefinition(projectID={Project.PLATTP},
    update=true,
    importance=DefTypes.Importance.LOW)
    @Test(groups={"simple"},
          description="Testing if XUnitReporter gets stack trace")
    public void testError() {
        throw new ConfigurationError("Just throwing an error");
    }

    @TestDefinition(projectID={Project.PLATTP},
                    testCaseID={""},
                    level=DefTypes.Level.SYSTEM)
    @Test(groups={"simple"},
          description="Tests to make sure that the compiler fails")
    public void testBadProjectToTestCaseID() {
        Assert.assertTrue(true);
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

    @DataProvider(name="anotherProvider")
    public Object[][] dataCreator() {
        Object[][] table = new Object[2][2];
        List<Object> row = new ArrayList<>();

        Object[] objs = {"Circle", 100.0};
        table[0] = objs;

        Object[] objs2 = {"Square", 200.0};
        table[1] = objs2;
        return table;
    }
}
