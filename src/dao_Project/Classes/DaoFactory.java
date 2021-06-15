package dao_Project.Classes;

import dao_Project.Classes.Interface.SellerDao;
import db.DB;

public class DaoFactory {
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}
