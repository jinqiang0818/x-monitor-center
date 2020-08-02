package com.test.xmonitorcenter.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "Payment")
public class Payment implements Serializable {
    @JacksonXmlProperty(localName = "id")
    private Long id;
    @JacksonXmlProperty(localName = "name")
    private String name;

}
