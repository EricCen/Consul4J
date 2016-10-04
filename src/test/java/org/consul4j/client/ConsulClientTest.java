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
    public void testKeyValueOperation(){
        testAddKeyValueToConsul();
        testGetValueByKeyFromConsul();
        testDeleteKeyValueFromConsul();
    }

    public void testAddKeyValueToConsul(){
        boolean result = consulClient.addKeyValueToConsul("localhost", "127.0.0.1");
        Assert.assertTrue(result);
    }

    public void testGetValueByKeyFromConsul(){
        String value = consulClient.getValueByKeyFromConsul("localhost");
        Assert.assertEquals("The value of key is not correct", "127.0.0.1", value);
    }

    public void testDeleteKeyValueFromConsul(){
        boolean result = consulClient.deleteKeyValueFromConsul("localhost");
        Assert.assertTrue(result);
    }

}
