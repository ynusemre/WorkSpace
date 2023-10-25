package com.example.XmlParser.controller;

import com.example.XmlParser.model.Message;
import com.example.XmlParser.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/messages")
public class MessageController {
    private final MessageService messageService;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/messageNames/{xsiType}")
    public ResponseEntity<List<String>> messageNameList(@PathVariable String xsiType){
        return ResponseEntity.ok(messageService.messageNameList(xsiType));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/xsiTypes")
    public ResponseEntity<List<String>> xsiTypeList(){
        return ResponseEntity.ok(messageService.xsiTypeList());
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/xmlMessageNamePart/{messageName}")
    public ResponseEntity<List<String>> xmlMessageNamePart(@PathVariable String messageName){
        return ResponseEntity.ok(messageService.xmlMessageNamePart(messageName));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/xmlMessageNamePartStructureList/{messageName}")
    public ResponseEntity<List<String>> xmlMessageNamePartStructureList(@PathVariable String messageName){
        return ResponseEntity.ok(messageService.xmlMessageNamePartStructureList(messageName));
    }


}
