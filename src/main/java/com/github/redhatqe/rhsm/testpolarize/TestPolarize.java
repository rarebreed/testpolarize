package com.github.redhatqe.rhsm.testpolarize;


import com.github.redhatqe.polarize.metadata.DefTypes.Project;
import com.github.redhatqe.polarize.metadata.TestDefinition;
import com.github.redhatqe.polarize.metadata.TestStep;
import org.testng.annotations.Test;

/**
 * Created by stoner on 6/28/16.
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
