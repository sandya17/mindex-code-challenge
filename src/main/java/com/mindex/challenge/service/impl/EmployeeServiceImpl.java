package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }
    
    @Override
    public Compensation createCompensation(Compensation compensation) {
        LOG.debug("Creating Compensation [{}]", compensation);

        compensation.setEmployeeId(UUID.randomUUID().toString());
        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }
    
    @Override
    public Compensation readCompensation(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Compensation compenstation = compensationRepository.findByEmployeeId(id);

        if (compenstation == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return compenstation;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

	@Override
	public ReportingStructure readReportingStructure(String id) {
		ReportingStructure employee_structure = new ReportingStructure();
		List<Employee> sub_emps = new ArrayList<Employee>();
		Employee employee = employeeRepository.findByEmployeeId(id);
		 if (employee == null) {
	            throw new RuntimeException("Invalid employeeId: " + id);
	        }
		 employee_structure.setEmployeeId(employee.getEmployeeId());
		 employee_structure.setFirstName(employee.getFirstName());
		 employee_structure.setLastName(employee.getLastName());
		 employee_structure.setDepartment(employee.getDepartment());
		 employee_structure.setPosition(employee.getPosition());
		 for(Employee emp : employee.getDirectReports()) {
			 Employee sub_emp = employeeRepository.findByEmployeeId(emp.getEmployeeId());
			 if(null != sub_emp) {
				 sub_emps.add(sub_emp);
			 }
		 }
		 employee_structure.setDirectReports(sub_emps);
		return employee_structure;
	}
}
