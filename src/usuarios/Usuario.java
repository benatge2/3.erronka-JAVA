package usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.DBconnect;

public class Usuario extends Pertsona {
	private double Saldo;
	private double HC;

	public Usuario() {
		super();
	}

	public Usuario(String nAN, String name, String apellido, String mail, String pass, double saldo, double hc) {
		super(nAN, name, apellido, mail, pass);
		this.Saldo = saldo;
		this.HC=hc;
	}

	public void informazioaJaso(String nan) {
		String tabla = "langileak";
		String Query = "SELECT * FROM "+tabla+" WHERE nan = "+nan;
		try (DBconnect obJConnection = new DBconnect()){
			Connection connection = obJConnection.getConnection();
			PreparedStatement cs = connection.prepareStatement(Query);
			ResultSet rs = cs.executeQuery(Query);

			while (rs.next()) {
               super.setNAN(rs.getString("nan"));
               super.setName(rs.getString("nombre"));
               super.setApellido(rs.getString("apellido"));
               super.setMail(rs.getString("email"));
               super.setPass(rs.getString("contraseña"));
               this.Saldo = rs.getDouble("saldo");
               this.HC = rs.getDouble("handicap");
            }
			rs.close();
            cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void informazioaGorde() {
		String tabla= "usuarios";
		String updateQuery = "UPDATE " + tabla + " SET saldo = ?, handicap = ? WHERE nan = '"+super.getNAN()+"'";
		try (DBconnect obJConnection = new DBconnect()){
			super.informazioaGorde(tabla);
			Connection connection = obJConnection.getConnection();
			PreparedStatement checkStatement = connection.prepareStatement(updateQuery);

			checkStatement.setDouble(1,this.Saldo);
			checkStatement.setDouble(2,this.HC);

			int rowsUpdated = checkStatement.executeUpdate();
			if (rowsUpdated > 0) {
		            System.out.println("Fila actualizada exitosamente.");
		        } else {
		            System.out.println("No se pudo actualizar la fila.");
		        }
			checkStatement.close();
			connection.close();
			obJConnection.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public double getSaldo() {
		return Saldo;
	}

	public void setSaldo(double saldo) {
		Saldo = saldo;
	}

	public double getHC() {
		return HC;
	}

	public void setHC(double hC) {
		HC = hC;
	}
}