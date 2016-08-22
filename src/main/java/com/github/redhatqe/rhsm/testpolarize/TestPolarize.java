package com.github.redhatqe.rhsm.testpolarize;


import com.github.redhatqe.polarize.metadata.Requirement;
import com.github.redhatqe.polarize.metadata.TestDefinition;
import com.github.redhatqe.polarize.metadata.TestStep;
import org.testng.annotations.Test;

/**
 * Created by stoner on 6/28/16.
 */
public class TestPolarize {
    public TestPolarize() {

    }

    @TestDefinition(projectID="RHEL6",
              testCaseID="RHEL6-35764",
              setup="Description of preconditions that must be true for test to run",
              teststeps={@TestStep(description="Fulfills requirement",
                                   expected="does not throw exception")},
              teardown="Description of what needs to be cleaned up after test runs",
              reqs={@Requirement(description="Example of a class Requirement")})
    @Test(groups={"testpolarize"},
          description="A simple test for testpolarize")
    public void testMethod() {
        System.out.println("In testMethod");
    }

    @TestDefinition(projectID="RedHatEnterpriseLinux7",
              reqs={@Requirement(description="Give requirement description here",
              reqtype="Nonfunctional")},
              setup="This is how you setup the test")
    public void testMethod2() {

    }
}
