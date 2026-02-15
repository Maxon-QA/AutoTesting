package ru.auto.db;


public class Employee {
    private Integer employeeId;
    private String name;
    private Integer departmentId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Employee(Integer employeeId, String name, Integer departmentId) {
        this.employeeId = employeeId;
        this.name = name;
        this.departmentId = departmentId;
    }
}

