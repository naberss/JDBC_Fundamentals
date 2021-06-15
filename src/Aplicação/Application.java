package Aplicação;

import java.util.List;

import dao_Project.Classes.DaoFactory;
import dao_Project.Classes.Department;
import dao_Project.Classes.Seller;
import dao_Project.Classes.Interface.SellerDao;

public class Application {

	public static void main(String[] args) {
		Department dept = new Department(1, "FINANCIAL");

		System.out.println(dept.toString());

		System.out.println("=== TEST 1: Seller findById ===");

		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = sellerDao.findById(1);
		System.out.println(seller);

		System.out.println("=== TEST 2: Seller findByDepartment ===");

		List<Seller> sellerList = sellerDao.findByDepartment1(dept);

		for (Seller rec : sellerList) {
			System.out.println(rec);
		}
		
		System.out.println("=== TEST 3: Seller findAll ===");
		
		sellerList = sellerDao.findAll();

		for (Seller rec : sellerList) {
			System.out.println(rec);
		}		
		
		

	}

}
