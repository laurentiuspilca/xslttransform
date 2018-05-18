package com.example.xslttransformexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;

@RestController
public class XSLTService {

    @Value("${xsltPath}")
    private String xsltPath;

    @RequestMapping(method = RequestMethod.POST, path = "/transform")
    public String transform(@RequestBody String xml) {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            StreamSource xsltPathStream = new StreamSource(new File(xsltPath));
            Transformer transformer = tFactory.newTransformer(xsltPathStream);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Result result = new StreamResult(baos);

            transformer.transform(new StreamSource(new ByteArrayInputStream(xml.getBytes())), result);

            return baos.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "EMPTY";
    }
}
