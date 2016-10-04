package org.consul4j.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Eric Cen on 2016/10/4.
 */
public class ConsulClientTest {
    private ConsulClient consulClient;

    @Before
    public void setUp(){
        consulClient = new ConsulClient("localhost",8500);
    }

    @Test
    public void testAddKeyValueToConsul(){
        boolean result = consulClient.addKeyValueToConsul("Java", "James Gosling");
        Assert.assertTrue(result);
    }

    @Test
    public void testGetValueByKeyFromConsul(){
        String value = consulClient.getValueByKeyFromConsul("language");
        Assert.assertEquals("The value of key is not correct", "java", value);
    }

    @Test
    public void testDeleteKeyValueFromConsul(){
        boolean result = consulClient.deleteKeyValueFromConsul("Java's Father");
        Assert.assertTrue(result);
    }

}
