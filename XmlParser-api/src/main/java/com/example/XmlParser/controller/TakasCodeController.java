package com.example.XmlParser.controller;

import com.example.XmlParser.service.TakasCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/takasCodes")
public class TakasCodeController {
    private final TakasCodeService takasCodeService;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping()
    public ResponseEntity<Set<String>> xsiTypeList(){
        return ResponseEntity.ok(takasCodeService.takasCodeList());
    }


    @GetMapping("/saveTakasCode")
    public ResponseEntity<Set<String>> saveTakasCode(){
        takasCodeService.saveTakasCode();
        return ResponseEntity.ok().build();
    }

}
