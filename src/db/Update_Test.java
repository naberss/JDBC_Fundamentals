package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update_Test {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DB.getConnection();
			ps = con.prepareStatement("UPDATE TALUNO SET COD_ALUNO = 135 WHERE COD_ALUNO = 35",
					PreparedStatement.RETURN_GENERATED_KEYS);
			int rowsAffected = ps.executeUpdate();

			System.out.println("Rows Affected: " + rowsAffected);

			ResultSet rs = ps.getGeneratedKeys();

			while (rs.next()) {
				System.out.println(rs.getString(1));
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeConnection();
			DB.closeStatement(ps);
		}

	}

}
