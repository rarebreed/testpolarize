package com.redhat.qe.rhsm.testpolarize;

import com.redhat.qe.rhsm.metadata.Requirement;
import com.redhat.qe.rhsm.metadata.TestCase;
import org.testng.annotations.Test;

/**
 * Created by stoner on 6/28/16.
 */
public class TestPolarize {
    public TestPolarize() {

    }

    @TestCase(projectID="RHEL6",
              testCaseID="RHEL6-35764",
              reqs={@Requirement(description="Example of a Requirement")})
    @Test(groups={"testpolarize"},
          description="A simple test for testpolarize")
    public void testMethod() {
        System.out.println("In testMethod");
    }

    @TestCase(projectID="RedHatEnterpriseLinux7", reqs={}, setup="This is how you setup the test")
    public void testMethod2() {

    }
}
