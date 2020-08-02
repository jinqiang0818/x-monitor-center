package com.test.xmonitorcenter.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.test.xmonitorcenter.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MonitorController {

    @GetMapping(path = "/monitor/test/{name}")
    public String testXml(@PathVariable("name") String name){
        log.info("name:"+ name);
        return name;
    }


    @GetMapping(path = "/monitor/xml/{name}",produces = MediaType.TEXT_XML_VALUE)
    public String testXml2(@PathVariable("name") String name){
        log.info("name:"+ name);
        return name;
    }

    @RequestMapping(value = "/monitor/aXml", method = RequestMethod.POST,consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String testAppliactionXml(@RequestBody User user) {
        log.info("test:parm:user:{}", JSON.toJSONString(user));
        user.setName("陈晓芳");
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        String requestBody  = null;
        try {
            requestBody  = xmlMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            log.error("error,e=",e);
        }
        return requestBody;
    }

    @PostMapping(value = "/monitor/bXml",consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public User testAppliactionXml2(@RequestBody User user) {
        log.info("test:parm:user:{}", JSON.toJSONString(user));
        user.setName("陈晓芳");
        return user;
    }

    @PostMapping(value = "/monitor/cXml",consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    public User testAppliactionXml3(@RequestBody User user) {
        log.info("test:parm:user:{}", JSON.toJSONString(user));
        user.setName("陈晓芳");
        log.info("12213313");
        return user;
    }
}
