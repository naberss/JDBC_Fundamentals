package dao_Project.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao_Project.Classes.Interface.SellerDao;
import db.DbException;

public class SellerDaoJDBC implements SellerDao {

	private Connection con;

	public SellerDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void insert(Seller dept) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller dept) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.con.prepareStatement(
					"SELECT SELL.*, DEPT.NAME AS DEP_NAME FROM DEPARTMENT DEPT,SELLER SELL WHERE SELL.DEPARTMENT_ID = DEPT.DEPARTMENT_ID AND SELL.ID = ?");
			ps.setInt(1, id);
			ps.execute();
			rs = ps.executeQuery();
			if (rs.next()) {
				return new Seller(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDouble(5),
						new Department(rs.getInt(6), rs.getString(7)));

			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
