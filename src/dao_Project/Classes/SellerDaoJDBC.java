package dao_Project.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao_Project.Classes.Interface.SellerDao;
import db.DB;
import db.DbException;

public class SellerDaoJDBC implements SellerDao {

	private Connection con;

	public SellerDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("INSERT INTO SELLER VALUES (?,?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setInt(1, seller.getId());
			ps.setString(2, seller.getName());
			ps.setString(3, seller.getEmail());
			ps.setDate(4, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(5, seller.getBaseSalary());
			ps.setInt(6, seller.getDept().getId());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					System.out.println("Inserted RowId: " + rs.getString(1));
				}
				DB.closeResultSet(rs);
			} else {
				System.out.println("No Rows Affected");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void update(Seller seller) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(
					"UPDATE SELLER SET NAME = ? ,EMAIL = ? ,BIRTHDATE = ? ,BASESALARY = ? ,DEPARTMENT_ID = ? WHERE SELLER.ID = ?",
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDept().getId());
			ps.setInt(6, seller.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM SELLER WHERE ID = ?");
			ps.setInt(1, id);
			int RowsAffected = ps.executeUpdate();

			System.out.println(RowsAffected + " updated rows");

		} catch (SQLException e) {
			e.printStackTrace();
		}

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
		Statement st = null;
		ResultSet rs = null;
		List<Seller> sellerList = new ArrayList<>();
		Map<Integer, Department> deptMap = new HashMap<>();
		try {

			st = con.createStatement();
			rs = st.executeQuery(
					"SELECT SELL.*, DEPT.NAME AS DEP_NAME FROM DEPARTMENT DEPT,SELLER SELL WHERE SELL.DEPARTMENT_ID = DEPT.DEPARTMENT_ID");

			while (rs.next()) {
				/*
				 * Se o departamento já existir, a variável deptAux pega ele e ignora a
				 * validação de inclusão no map, desta forma departamentos iguais não são
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
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return sellerList;
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
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
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
				 * Se o departamento já existir, a variável deptAux pega ele e ignora a
				 * validação de inclusão no map, desta forma departamentos iguais não são
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
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
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
