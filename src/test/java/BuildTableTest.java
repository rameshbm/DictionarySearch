package test.java;

import main.java.com.ramesh.dictionarysearch.BuildTable;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

/*
 * CopyRight: © 2017 Permission granted for acoadamic purpuse only, any type of reproduction is prohibited
 *
 * Author: Ramesh Manchiknati
 *
 * Company: Personal Development
 *
 *Created by Ramesh on 7/11/15..
 *
 */
public class BuildTableTest {
    @Test
    public void testGetTransitionsTable() throws Exception {

        BuildTable bt = new BuildTable("src/test/resources/TestBuildTableMtrx.txt"," ");
        int retSize  = bt.getTransitionsTable().getTransitions().size();
        Assert.assertEquals(retSize, 64, "No of transision expect " + 4 + " returned by " + retSize);
    }
}