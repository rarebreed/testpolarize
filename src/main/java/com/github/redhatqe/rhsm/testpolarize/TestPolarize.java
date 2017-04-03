package com.github.redhatqe.rhsm.testpolarize;


import com.github.redhatqe.polarize.metadata.*;
import com.github.redhatqe.polarize.metadata.DefTypes.Project;
import com.github.redhatqe.polarize.metadata.DefTypes.PosNeg;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * TODO: Running list of things to test
 *
 * - Create a test that fails with an exception, and make sure that the xunit file has this exception
 * - Verify that for every element in the mapping.json file, the XML exists with that ID
 * - Verify that for every XML description file in testcases folder, it exists in the mapping.json
 * - Ensure that a file is created that lists every method whose annotation is testCaseID="", but the XML has it
 * - Ensure that the indexes of the project array matches the inde
 *
 * Future work:
 * - Need a TestCase query method for updating.
 *   - We only want to change what is different
 *   - Verify that the
 */
public class TestPolarize {
    public TestPolarize() {

    }

    @TestDefinition(projectID=Project.PLATTP,
              setup="Description of preconditions that must be true for test to run",
              teststeps={@TestStep(description="Fulfills requirement",
                                   expected="does not throw exception")},
              teardown="Description of what needs to be cleaned up after test runs",
              reqs={})
    @Test(groups={"testpolarize"},
          description="A simple test for testpolarize")
    public void testMethod() {
        System.out.println("In testMethod");
    }

    @TestDefinition(projectID=Project.PLATTP,    // required
            testCaseID="",                       // if empty or null, make request to WorkItem Importer tool
            importance=DefTypes.Importance.HIGH, // defaults to high  if not given
            posneg=PosNeg.POSITIVE,              // defaults to positive if not given
            level= DefTypes.Level.SYSTEM,     // defaults to component if not given
            //linkedWorkItems={@LinkedItem(workitemId="PLATTP-9520 ",         // Required
            //        project=Project.PLATTP,                                 // Required. What Project to go under
            //        role=DefTypes.Role.RELATES_TO)},                        // Required. Role type
            // If testtype is FUNCTIONAL, subtype1 and 2 must be of type EMPTY.
            testtype=@TestType(testtype= DefTypes.TestTypes.NONFUNCTIONAL,  // Defaults to FUNCTIONAL
                    subtype1= DefTypes.Subtypes.COMPLIANCE,      // Defaults to EMPTY (see note)
                    subtype2= DefTypes.Subtypes.EMPTY),          // Defaults to EMPTY (see note)
            setup="Description of any preconditions that must be established for test case to run",
            tags="tier1 polarize_test foo",
            teardown="The methods to clean up after a test method",
            update=true,
            automation=DefTypes.Automation.AUTOMATED)  // if not given this defaults to AUTOMATED)
    @Test(groups={"simple"},
            description="Test for reporter code",
            dataProvider="simpleProvider")
    public void yetAnotherTestMethod() {

    }

    @Test( groups={"testpolarize"}
         , description = "This test should show up in the auditing tool")
    public void testWithoutTD() {
        // TODO: open the /tmp/polarize-auditing.txt, and check this method name is there
        Assert.assertTrue(true);
    }

    @Test( groups={"no-test-definition"}
         , description="Another test without @TestDefinition")
    public void testAnotherWithoutTD() {
        Assert.assertTrue(true);
    }
}
