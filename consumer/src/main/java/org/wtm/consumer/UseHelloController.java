package org.wtm.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.wtm.commons.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

@RestController
public class UseHelloController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;



    @GetMapping("/hello1")
    public String hello1(){
        HttpURLConnection con=null;
        try {
            URL url=new URL("http://localhost:1113/hello");
            con=(HttpURLConnection) url.openConnection();
            if(con.getResponseCode()==200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                return  s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "error";
    }

    @GetMapping("/hello2")
    public String hello2(){
        HttpURLConnection con=null;
        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = provider.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://").append(host).append(":").append(port).append("/hello");
        String forObject = restTemplate.getForObject(stringBuffer.toString(), String.class);
/*        try {
            URL url=new URL(stringBuffer.toString());
            con=(HttpURLConnection) url.openConnection();
            if(con.getResponseCode()==200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                return  s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return  forObject;
    }


    //实现线性均衡
    int count=0;
    @GetMapping("/hello3")
    public String hello3(){
        HttpURLConnection con=null;
        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = provider.get((count++)%provider.size());
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://").append(host).append(":").append(port).append("/hello");
        try {
            URL url=new URL(stringBuffer.toString());
            con=(HttpURLConnection) url.openConnection();
            if(con.getResponseCode()==200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                return  s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "error";
    }




    @GetMapping("/hello4")
    public void hello4(){
        String susuzimo = restTemplate.getForObject("http://localhost:1113/hello2?name={1}", String.class, "susuzimo");
        System.out.println(susuzimo);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:1113/hello2?name={1}", String.class, "suzimo");
        String body = responseEntity.getBody();
        System.out.println("body"+body);
        HttpStatus statusCode = responseEntity.getStatusCode();
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println("statusCode"+statusCode);
        System.out.println("statusCodeValue"+statusCodeValue);

        HttpHeaders headers = responseEntity.getHeaders();
        Set<String> keySet = headers.keySet();
        System.out.println("--------------headers--------------");
        for(String s :keySet){
            System.out.println(s+":"+headers.get(s));
        }
    }


    @GetMapping("/hello11")
    public void hello11(){


        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:1113/hello3?name={1}&age={1}", String.class, "111", "222");
        String body = forEntity.getBody();
        System.out.println(body);
    }




    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate getRestTemplate;
    @GetMapping("/hello5")
    public void hello5() throws UnsupportedEncodingException {
        String susuzimo = getRestTemplate.getForObject("http://provider/hello3?name={1}&age={1}", String.class, "susuzimo","1111");
        System.out.println(susuzimo);
        Map<String,Object> map=new HashMap<>();
        map.put("name","suzimo");
        susuzimo=getRestTemplate.getForObject("http://provider/hello2?name={name}",String.class,map);

//
//        String url="http://localhost:1113/hello2?name="+ URLEncoder.encode("suzimo","utf-8");
//        URI uri = URI.create(url);
//        String  susuzimo=restTemplate.getForObject(uri,String.class);
//        System.out.println(susuzimo);
        System.out.println(susuzimo);
    }


    @GetMapping("/hello20")
    public String hello20() throws UnsupportedEncodingException {

        String susuzimo=getRestTemplate.getForObject("http://provider/hello",String.class);
        return susuzimo;
    }




    //post
    @GetMapping("/hello6")
    public void hello6(){
        MultiValueMap<String,Object> multiValueMap=new LinkedMultiValueMap<>();
        multiValueMap.add("username","suzimo");
        multiValueMap.add("id","1");
        multiValueMap.add("password","123456");
        User user = restTemplate.postForObject("http://localhost:1113/user1", multiValueMap, User.class);
        System.out.println(user);
        user.setId(99);
        User user1 = restTemplate.postForObject("http://localhost:1113/user2", user, User.class);
        System.out.println(user1);
    }

    @GetMapping("/hello7")
    public void hello7(){
        MultiValueMap<String,Object> multiValueMap=new LinkedMultiValueMap<>();
        multiValueMap.add("username","suzimo");
        multiValueMap.add("id","1");
        multiValueMap.add("password","123456");

        URI uri = restTemplate.postForLocation("http://localhost:1113/register", multiValueMap, User.class);

        String forObject = restTemplate.getForObject(uri, String.class);
        System.out.println(forObject);

    }


    @GetMapping("/hello8")
    public void hello8(){
        MultiValueMap<String,Object> multiValueMap=new LinkedMultiValueMap<>();
        multiValueMap.add("username","suzimo");
        multiValueMap.add("id","1");
        multiValueMap.add("password","123456");
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity("http://localhost:1113/user1", multiValueMap, User.class);
        System.out.println(userResponseEntity.getBody());
        User user=new User();
        user.setId(99);
        ResponseEntity<User> userResponseEntity1 = restTemplate.postForEntity("http://localhost:1113/user2", user, User.class);
        System.out.println(userResponseEntity1.getBody());
    }


    @GetMapping("/hello9")
    public void hello9(){

        MultiValueMap<String,Object> multiValueMap=new LinkedMultiValueMap<>();
        multiValueMap.add("username","suzimo");
        multiValueMap.add("id","1");
        multiValueMap.add("password","123456");
        restTemplate.put("http://localhost:1113/user1", multiValueMap);
        User user=new User();
        user.setId(99);
        restTemplate.put("http://localhost:1113/user2", user);
    }



    @GetMapping("/hello10")
    public void hello10(){
        /*两个参数*/
        getRestTemplate.delete("http://provider/user1?id={1}&id1={1}","1","2");
        getRestTemplate.delete("http://provider/user2/{1}/{1}","1","2");
        /*一个参数*/
        getRestTemplate.delete("http://provider/user1?id={1}","1");
        getRestTemplate.delete("http://provider/user2/{1}","1");
    }



    @GetMapping("/hello12")
    public void hello12(){
        MultiValueMap<String,Object> multiValueMap=new LinkedMultiValueMap<>();
        multiValueMap.add("username","suzimo");
        multiValueMap.add("id","1");
        multiValueMap.add("password","123456");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", "1");
        ResponseEntity<String > response = restTemplate.exchange("http://localhost:1113/user3/{id}", HttpMethod.DELETE, null, String.class, paramMap);
        String result = response.getBody();
        System.out.println(result+"hello12");
    }

    @GetMapping("/hello13")
    public void hello13(){
        User user=new User();
        user.setId(1111);
        restTemplate.put("http://localhost:1113/user3/{1}", user, 99);
    }


    @GetMapping("/hello14")
    public void hello14(){

        MultiValueMap<String,Object> multiValueMap=new LinkedMultiValueMap<>();
        multiValueMap.add("username","suzimo");
        multiValueMap.add("id","1");
        multiValueMap.add("password","123456");
        restTemplate.delete("http://localhost:1113/user3", multiValueMap);

    }
}
