package com.appscharles.libs.updater.comparators;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Version comparator test.
 */
public class VersionComparatorTest {

    /**
     * Should version equal to version two.
     */
    @Test
    public void shouldVersionEqualToVersionTwo(){
        VersionComparator vc = new VersionComparator();
        String version1 = "1.0.0.0-dev0";
        String version2 = "1.0.0.0-dev0";
        Assert.assertEquals(vc.compare(version1, version2), 0);
    }

    /**
     * Should version 1 less than version 2.
     */
    @Test
    public void shouldVersion1LessThanVersion2(){
        VersionComparator vc = new VersionComparator();
        String version1 = "1.0.0.0-dev0";
        String version2 = "1.0.0.0-dev1";
        Assert.assertEquals(vc.compare(version1, version2), -1);
    }

    /**
     * Should version 1 greater than version 2.
     */
    @Test
    public void shouldVersion1GreaterThanVersion2(){
        VersionComparator vc = new VersionComparator();
        String version1 = "1.0.0.0-dev1";
        String version2 = "1.0.0.0-dev0";
        Assert.assertEquals(vc.compare(version1, version2), 1);
    }
}