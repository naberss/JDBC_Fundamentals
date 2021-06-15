package dao_Project.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				return instantiateSeller(rs, instantiateDepartment(rs));

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

	public List<Seller> findByDepartment(Department dept) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Seller> sellerList = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT SELL.*, DEPT.NAME AS DEP_NAME FROM DEPARTMENT DEPT,SELLER SELL WHERE SELL.DEPARTMENT_ID = DEPT.DEPARTMENT_ID AND SELL.DEPARTMENT_ID = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, dept.getId());
			rs = ps.executeQuery();

			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				rs.beforeFirst();
				while (rs.next()) {
					sellerList.add(instantiateSeller(rs, department));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sellerList;

	}

	public List<Seller> findByDepartment1(Department dept) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Seller> sellerList = new ArrayList<>();
		Map<Integer, Department> deptMap = new HashMap<>();
		try {
			ps = con.prepareStatement(
					"SELECT SELL.*, DEPT.NAME AS DEP_NAME FROM DEPARTMENT DEPT,SELLER SELL WHERE SELL.DEPARTMENT_ID = DEPT.DEPARTMENT_ID AND SELL.DEPARTMENT_ID = ?");
			ps.setInt(1, dept.getId());
			rs = ps.executeQuery();

			while (rs.next()) {

				/*
				 * Se o departamento j� existir, a vari�vel deptAux pega ele e ignora a
				 * valida��o de inclus�o no map, desta forma departamentos iguais n�o s�o
				 * instanciados mais de uma vez
				 */
				Department deptAux = deptMap.get(rs.getInt("DEPARTMENT_ID"));
				if (deptAux == null) {
					deptAux = instantiateDepartment(rs);
					deptMap.put(rs.getInt("DEPARTMENT_ID"), deptAux);
				}

				sellerList.add(instantiateSeller(rs, deptAux));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sellerList;

	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("DEPARTMENT_ID"), rs.getString("DEP_NAME"));
	}

	private Seller instantiateSeller(ResultSet rs, Department dept) throws SQLException {
		return new Seller(rs.getInt("ID"), rs.getString("NAME"), rs.getString("EMAIL"), rs.getDate("BIRTHDATE"),
				rs.getDouble("BASESALARY"), dept);
	}

}
