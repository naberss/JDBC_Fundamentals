package Aplicação;

import java.util.List;

import dao_Project.Classes.DaoFactory;
import dao_Project.Classes.Department;
import dao_Project.Classes.Interface.DepartmentDao;

public class Aplicação_Department {

	public static void main(String[] args) {

		DepartmentDao dept = DaoFactory.createDepartmentDao();

		System.out.println("=== TEST 1: Department DeleteById ===");

		dept.deleteById(3);

		System.out.println("=== TEST 2: Department InsertByDepartment ===");

		Department dept2 = new Department(3, "CONTABIL");
		dept.insert(dept2);

		System.out.println("=== TEST 3: Department UpdateByDepartment ===");
		Department deptAux = new Department(3, "RH");
		dept.update(deptAux);

		System.out.println("=== TEST 4: Department FindById ===");
		deptAux = dept.findById(1);
		System.out.println(deptAux);

		System.out.println("=== TEST 5: Department FindAll ===");
		List<Department> deptList = dept.findAll();

		for (Department rec : deptList) {
			System.out.println(rec);
		}

	}

}
