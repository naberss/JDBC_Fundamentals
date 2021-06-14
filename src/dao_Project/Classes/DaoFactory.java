package dao_Project.Classes;

import dao_Project.Classes.Interface.SellerDao;

public class DaoFactory {
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC();
	}
}
