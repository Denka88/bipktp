package com.denka88.bipktp.repo;

import com.denka88.bipktp.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepo extends JpaRepository<Record, Long> {
}
