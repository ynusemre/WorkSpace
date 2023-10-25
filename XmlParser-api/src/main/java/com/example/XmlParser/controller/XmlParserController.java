package com.example.XmlParser.controller;


import com.example.XmlParser.model.Message;
import com.example.XmlParser.service.XmlParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/xmlParsers")
public class XmlParserController {
    private final XmlParserService xmlParserService;

    @GetMapping("/messageXmlParsing")
    public ResponseEntity<List<Message>> messageXmlParsing(){
        return ResponseEntity.ok(xmlParserService.messageXmlParsing());
    }

    @GetMapping("/structureRefsXmlParsing")
    public ResponseEntity<List<String>> structureRefsXmlParsing(){
        return ResponseEntity.ok(xmlParserService.structureRefsXmlParsing());
    }

    @GetMapping("/messageXmlParsing2")
    public ResponseEntity<List<String>> messageXmlParsing2(){
        return ResponseEntity.ok(xmlParserService.messageXmlParsing2());
    }

}
