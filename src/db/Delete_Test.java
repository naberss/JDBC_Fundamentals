package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Delete_Test {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DB.getConnection();
			ps = con.prepareStatement("DELETE FROM TALUNO WHERE COD_ALUNO = 135",
					PreparedStatement.RETURN_GENERATED_KEYS);

			int rowsAffected = ps.executeUpdate();

			System.out.println("Rows Affected: " + rowsAffected);

			if (rowsAffected > 0) {

				ResultSet rs = ps.getGeneratedKeys();

				while (rs.next()) {
					System.out.println("RowId: " + rs.getString(1));
				}

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeConnection();
			DB.closeStatement(ps);
		}

	}

}
