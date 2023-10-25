package com.example.XmlParser.service;

import com.example.XmlParser.model.Field;
import com.example.XmlParser.model.InternalModule;
import com.example.XmlParser.model.Message;
import com.example.XmlParser.repository.FieldRepository;
import com.example.XmlParser.repository.InternalModuleRepository;
import com.example.XmlParser.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final FieldRepository fieldRepository;
    private final InternalModuleRepository internalModuleRepository;

    public List<String> messageNameList(String xsiType){
        List<Message> messages=messageRepository.findAll();
        List<String> messageNames= new ArrayList<>();



        for (int i = 0 ; i < messages.size();i++){
            if(messages.get(i).getXsiType().equals(xsiType)){
                messageNames.add(messages.get(i).getName());
            }
        }

        Collections.sort(messageNames, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                int num1 = Integer.parseInt(o1.substring(2));
                int num2 = Integer.parseInt(o2.substring(2));
                return Integer.compare(num1, num2);
            }
        });

        return messageNames;
    }

    public List<String> xsiTypeList(){
        List<Message> messages=messageRepository.findAll();
        Set<String> xsiTypes= new HashSet<>();

        for (int i = 0 ; i < messages.size();i++){
            xsiTypes.add(messages.get(i).getXsiType());
        }

        List<String> sortedList = new ArrayList<>(xsiTypes);
        Collections.sort(sortedList);
        return sortedList;
    }


    public List<String> xmlMessageNamePart(String messageName){
        List<String> xmlMessageNamePart= new ArrayList<>();

        List<InternalModule> internalModules=internalModuleRepository.getAllInternalModules();

        for (int i = 0 ; i < internalModules.size();i++){
            String name="<name>"+messageName+"</name>";

            if(internalModules.get(i).getPart().contains(name)){
                xmlMessageNamePart.add(0,internalModules.get(i).getPart());
            }
        }
        return xmlMessageNamePart;
    }

    private String xmlMessageNamePartString(String messageName){
        List<InternalModule> internalModules=internalModuleRepository.getAllInternalModules();

        String xmlMessageNamePart= "";

        for (int i = 0 ; i < internalModules.size();i++){
            String name="<name>"+messageName+"</name>";

            if(internalModules.get(i).getPart().contains(name)){
                xmlMessageNamePart=internalModules.get(i).getPart();
            }
        }
        return xmlMessageNamePart;
    }

    public List<String> xmlMessageNamePartStructureList(String messageName){
        List<Field> fieldList=fieldRepository.getAllFields();
        String xmlMessageNamePart=xmlMessageNamePartString(messageName);

        List<String> results= new ArrayList<>();

        List<String> structureRefs = new ArrayList<>();
        Pattern pattern = Pattern.compile("<structure_ref>(.*?)</structure_ref>");
        Matcher matcher = pattern.matcher(xmlMessageNamePart);

        while (matcher.find()) {
            structureRefs.add(matcher.group(1));
        }


        for (int i =0 ; i < structureRefs.size() ; i++){
            String name="<name>"+structureRefs.get(i)+"</name>";
            for (int j = 0 ; j < fieldList.size() ; j++){
                if(fieldList.get(j).getPart().contains(name)){
                    results.add(fieldList.get(j).getPart());
                }
            }
        }

        return results;
    }

}
