package test.java;

import main.java.com.ramesh.dictionarysearch.TransitionTable;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

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
public class TransitionTableTest {

    @Test
    public void testGetWords() throws Exception {
        TransitionTable tt =  new TransitionTable();
        int trRow = 0;
        int trCol = 0;
        for(String  st: tt.getTrans("hello"))
                tt.getTransitions().put(st,String.format("%03d",trRow)+String.format("%03d",trCol++)+String.format("%03d",trRow)+String.format("%03d",trCol));
        Assert.assertTrue(tt.isWord("hello"), "Test isWord fail to find hello ");
    }

    @Test
    public void testGetTrans() throws Exception {
        TransitionTable tt =  new TransitionTable();
        List<String> lt =  tt.getTrans("hello");
        Assert.assertEquals(lt.size(), 4, "Trnsitions Returned by getTrans not matched expected " + 4 + " returned  " + lt.size());
        Assert.assertTrue(lt.contains("ll"), "Trnsition ll not found ");
    }
}