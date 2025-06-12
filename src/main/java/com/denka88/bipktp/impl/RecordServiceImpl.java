package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.RecordDto;
import com.denka88.bipktp.repo.RecordRepo;
import com.denka88.bipktp.model.Record;
import com.denka88.bipktp.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    
    private final RecordRepo recordRepo;
    
    @Override
    public List<Record> findAll() {
        return recordRepo.findAll();
    }

    @Override
    public Optional<Record> findById(Long id) {
        return recordRepo.findById(id);
    }

    @Override
    public Record save(RecordDto recordDto) {
        Record record = new Record();
        record.setTitle(recordDto.getTitle());
        record.setHomework(recordDto.getHomework());
        record.setHours(recordDto.getHours());
        record.setEquipment(recordDto.getEquipment());
        record.setLessonType(recordDto.getLessonType());
        record.setTeachMethods(recordDto.getTeachMethods());
        record.setChapter(recordDto.getChapter());
        recordRepo.save(record);
        
        return record;
    }

    @Override
    public void deleteById(Long id) {
        recordRepo.deleteById(id);
    }

    @Override
    public void update(Record record) {
        Record updatedRecord = recordRepo.findById(record.getId()).orElse(null);
        if (updatedRecord == null) {
            throw new IllegalArgumentException("Запись не найдена");
        }
        updatedRecord.setTitle(record.getTitle());
        updatedRecord.setHomework(record.getHomework());
        updatedRecord.setHours(record.getHours());
        updatedRecord.setEquipment(record.getEquipment());
        updatedRecord.setLessonType(record.getLessonType());
        updatedRecord.setTeachMethods(record.getTeachMethods());
        updatedRecord.setChapter(record.getChapter());
        recordRepo.save(updatedRecord);
    }
}
