package com.example.XmlParser.service;

import com.example.XmlParser.model.TakasCode;
import com.example.XmlParser.repository.TakasCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TakasCodeService {
    private final TakasCodeRepository takasCodeRepository;

    public Set<String> takasCodeList(){
        Set<String> takasCodes= new HashSet<>();


        takasCodes.add("se");
        takasCodes.add("ps");
        takasCodes.add("cm");


        return takasCodes;
    }

    public void saveTakasCode(){
        TakasCode takasCode1=new TakasCode();
        takasCode1.setName("se");
        takasCodeRepository.save(takasCode1);

        TakasCode takasCode2=new TakasCode();
        takasCode2.setName("ps");
        takasCodeRepository.save(takasCode2);

        TakasCode takasCode3=new TakasCode();
        takasCode3.setName("cm");
        takasCodeRepository.save(takasCode3);
    }


}
