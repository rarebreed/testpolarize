package com.github.redhatqe.rhsm.testpolarize;


import com.github.redhatqe.polarize.metadata.DefTypes.Project;
import com.github.redhatqe.polarize.metadata.TestDefinition;
import com.github.redhatqe.polarize.metadata.TestStep;
import org.testng.annotations.Test;

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
    reqs={})
    @TestDefinition(projectID=Project.RHEL6,
              testCaseID="RHEL6-35764",
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

    @TestDefinition(projectID=Project.RedHatEnterpriseLinux7,
                    setup="This is how you setup the test",
                    description="Since this is not an @Test annotated method, must provide description")
    public void testMethod2() {

    }
}
