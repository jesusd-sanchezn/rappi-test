package com.rappi.test.rappi_test;

import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by tomas castillo on 21/01/2017.
 */
public class AppTestSuite extends TestSuite{
    public static  Test suite(){
        return new TestSuiteBuilder(AppTestSuite.class).includeAllPackagesUnderHere().build();
    }

    public AppTestSuite(){
        super();
    }
}
