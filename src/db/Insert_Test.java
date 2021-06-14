package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Insert_Test {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DB.getConnection();
			ps = con.prepareStatement("INSERT INTO TALUNO VALUES (?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, 48);
			ps.setString(2, "PEDRIN");
			ps.setString(3, "VITORIA");
			ps.setString(4, "29015-400");
		 /* ps.setDate(5,new java.sql.date(sdf.parse("03/03/1998").getTime())) */

			int rowsAffected = ps.executeUpdate();

			System.out.println("Rows Affected: " + rowsAffected);

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				while (rs.next()) {
					System.out.println(rs.getString(1));// ORACLE ROWID
				}
				DB.closeResultSet(rs);

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeConnection();
			DB.closeStatement(ps);

		}

	}

}
