package com.mindex.challenge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mindex.challenge.data.ReportingStructure;

@Repository
public interface ReportingStructureRepository extends MongoRepository<ReportingStructure, String> {
	ReportingStructure findByEmployeeId(String employeeId);
}
