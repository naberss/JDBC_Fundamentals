package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction_Test {
	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DB.getConnection();
			st = con.createStatement();
			con.setAutoCommit(false);
			rs = st.executeQuery("UPDATE TALUNO SET COD_ALUNO = 1819 WHERE COD_ALUNO = 47");

			int x = 1;
			if (x == 1) {
				throw new SQLException("Fake error");
			}

			rs = st.executeQuery("UPDATE TALUNO SET COD_ALUNO = 666 WHERE COD_ALUNO = 44");
			con.commit();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
				con.rollback();
				throw new DbException("Erro no programa, rollback acionado");
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback! Causado por - " + e.getStackTrace());
			}

		}

		finally {
			DB.closeConnection();
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}
