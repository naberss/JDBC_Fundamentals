package dao_Project.Classes.Interface;

import java.util.List;

import dao_Project.Classes.Department;
import dao_Project.Classes.Seller;

public interface SellerDao {

	void insert(Seller dept);

	void update(Seller dept);

	void deleteById(Integer id);

	Seller findById(Integer id);

	List<Seller> findAll();
	
	List<Seller> findByDepartment(Department dept);
	
	List<Seller> findByDepartment1(Department dept);

}
