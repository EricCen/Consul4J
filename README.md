# Consul4J
Consul4J is a Java Client to Consul.

<b>API Usage</b>
<hr>
<b>Key/Value Operation</b>
`ConsulClient consulClient = new ConsulClient("YOUR_CONSUL_HOST", YOUR_CONSUL_PORT);

 String value = consulClient.getValueByKeyFromConsul("YOUR_KEY");
 
 consulClient.addKeyValueToConsul("YOUR_KEY", "YOUR_VALUE");
 
 consulClient.deleteKeyValueFromConsul("YOUR_KEY");
`
  
