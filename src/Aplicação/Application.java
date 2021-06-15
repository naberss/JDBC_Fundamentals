package Aplicação;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

		System.out.println("=== TEST 3: Seller Insert ===");

		seller.setId(1589);

		sellerDao.insert(seller);

		System.out.println("=== TEST 4: Update ===");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");

		seller.setName("MARCONES");
		seller.setEmail("MARQUIN_GENTEFINA@GMAIL.COM");
		try {
			seller.setBirthDate(sdf.parse("25/03/2001"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		seller.getDept().setId(2);
		seller.setBaseSalary(130.0);

		sellerDao.update(seller);
		
		System.out.println("=== TEST 3: Seller Delete ===");
		
		sellerDao.deleteById(1589);
		
		
		
		

	}

}
