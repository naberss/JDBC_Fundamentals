package dao_Project.Classes.Interface;

import java.util.List;

import dao_Project.Classes.Department;

public interface DepartmentDao {

	void insert(Department dept);

	void update(Department dept);

	void deleteById(Integer id);

	Department findById(Integer id);

	List<Department> findAll();

}
