package Aplica��o;

import dao_Project.Classes.DaoFactory;
import dao_Project.Classes.Department;
import dao_Project.Classes.Seller;
import dao_Project.Classes.Interface.SellerDao;

public class Application {

	public static void main(String[] args) {
		Department dept = new Department(1, "Financial");

		System.out.println(dept.toString());

		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = sellerDao.findById(1);
		System.out.println(seller);

	}

}
