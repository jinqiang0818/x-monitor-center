package com.test.xmonitorcenter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.xmonitorcenter.bean.Payment;
import com.test.xmonitorcenter.bean.User;
import com.test.xmonitorcenter.constans.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class RemoteMonitorController {

    private final static String PAY_URL = "http://localhost:8001/";

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value = "/remoteMonitor/xml",consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    public User testAppliactionXml3(@RequestBody User user) {
        log.info("test:parm:user:{}", JSON.toJSONString(user));
        user.setName("陈晓芳");
        CommonResult commonResult = restTemplate.getForObject(PAY_URL + "payment/getPaymentById/" + user.getUid(), CommonResult.class);
        Object data = commonResult.getData();
        if (data == null) {
            return user;
        }
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");

        user.setUid(Long.parseLong(id));
        user.setName(name);

        log.info("12213313");
        return user;
    }

    @PostMapping(value = "/remoteMonitor2/xml",consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    public User testAppliactionXml4(@RequestBody User user) {
        log.info("test:parm:user:{}", JSON.toJSONString(user));
        user.setName("陈晓芳");

        Payment paymentParm = new Payment();
        paymentParm.setId(user.getUid());
        
        XmlMapper xmlMapper = new XmlMapper();
        String userXmlString = null;
        try {
            userXmlString = xmlMapper.writeValueAsString(paymentParm);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("userXmlString:{}", userXmlString);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        //请求体
        HttpEntity<String> formEntity = new HttpEntity<>(userXmlString, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAY_URL + "/payment/getPaymentById2", formEntity, String.class);

        String body = responseEntity.getBody();

        XmlMapper xmlMapper2 = new XmlMapper();
        Payment resultPayment = null;
        try {
            resultPayment = xmlMapper2.readValue(body, Payment.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        user.setName(resultPayment.getName());
        log.info("response:{}",JSON.toJSONString(responseEntity));

        log.info("12213313");
        return user;
    }
}
