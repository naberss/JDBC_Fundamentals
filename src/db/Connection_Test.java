package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection_Test {
	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM TALUNO");

			while (rs.next()) {

				System.out.println(rs.getInt("COD_ALUNO") + " / " + rs.getString("NOME"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		finally {
			DB.closeConnection();
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}
