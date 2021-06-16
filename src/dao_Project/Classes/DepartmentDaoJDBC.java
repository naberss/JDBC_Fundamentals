package dao_Project.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao_Project.Classes.Interface.DepartmentDao;
import db.DbException;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection con;

	public DepartmentDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void insert(Department dept) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("INSERT INTO DEPARTMENT VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, dept.getId());
			ps.setString(2, dept.getName());

			int RowsAffected = ps.executeUpdate();

			if (RowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				System.out.println("Inserted RowId: " + rs.getString(1));
			} else {
				throw new DbException("No Rows Affected");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Department dept) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE DEPARTMENT SET NAME = ? WHERE DEPARTMENT_ID = ?",
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, dept.getName());
			ps.setInt(2, dept.getId());
			int RowsAffected = ps.executeUpdate();

			if (RowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				System.out.println("TotalRows Affected: " + RowsAffected);
				while (rs.next()) {
					System.out.println("Affected RowId: " + rs.getString(1));
				}
			} else {
				throw new DbException("No Rows Affected");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM DEPARTMENT WHERE DEPARTMENT_ID = ?");
			ps.setInt(1, id);
			int RowsAffected = ps.executeUpdate();

			if (RowsAffected > 0) {
				System.out.println(RowsAffected + " updated rows");
			} else {
				throw new DbException("No Rows Affected");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		Department dept = new Department();
		try {
			ps = con.prepareStatement("SELECT * FROM DEPARTMENT WHERE DEPARTMENT_ID = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs != null) {

				rs.next();

				dept = instantiateDepartment(rs);

			} else {
				throw new DbException("No Rows Selected");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dept;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		List<Department> deptList = new ArrayList<Department>();
		try {
			ps = con.prepareStatement("SELECT * FROM DEPARTMENT");
			ResultSet rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					deptList.add(instantiateDepartment(rs));
				}
			} else {
				throw new DbException("No Departments Found!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return deptList;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("DEPARTMENT_ID"), rs.getString("NAME"));
	}

}
