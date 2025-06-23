package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.RecordDto;
import com.denka88.bipktp.model.Record;

import java.util.List;
import java.util.Optional;

public interface RecordService {
    
    List<Record> findAll();
    Optional<Record> findById(Long id);
    Record save(RecordDto recordDto);
    void deleteById(Long id);
    void update(Record record, List<Long> teachMethodsIds);
    
}
