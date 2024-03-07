package usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.DBconnect;

public class Langile extends Pertsona {
	private int CP;
	private String Kalea;
	private String KK;
	private boolean Admin;

	public Langile() {
		super();
	}

	public Langile(String nAN, String name, String apellido, String mail, String pass, int cp, String kalea, String kk, boolean admin) {
		super(nAN, name, apellido, mail, pass);
		this.CP=cp;
		this.Kalea = kalea;
		this.KK=kk;
		this.Admin=admin;
	}

	public void informazioaJaso(String nan) {
		String tabla = "langileak";
		String Query = "SELECT * FROM "+tabla+" WHERE nan = '"+nan+"'";
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
               this.CP = rs.getInt("codigopostal");
               this.Kalea = rs.getString("calle");
               this.KK = rs.getString("kontukorrontea");
               this.Admin = rs.getBoolean("admin");
            }
			rs.close();
            cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void informazioaGorde() {
		String tabla= "langileak";
		String updateQuery = "UPDATE " + tabla + " SET codigopostal = ?, calle = ?, kontukorrontea = ?, admin = ? WHERE nan = "+super.getNAN();
		try (DBconnect obJConnection = new DBconnect()){
			super.informazioaGorde(tabla);
			Connection connection = obJConnection.getConnection();
			PreparedStatement checkStatement = connection.prepareStatement(updateQuery);

			checkStatement.setInt(6,this.CP);
			checkStatement.setString(7,this.Kalea);
			checkStatement.setString(8,this.KK);
			checkStatement.setBoolean(9,this.Admin);

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

	public int getCP() {
		return CP;
	}

	public void setCP(int cP) {
		CP = cP;
	}

	public String getKalea() {
		return Kalea;
	}

	public void setKalea(String kalea) {
		Kalea = kalea;
	}

	public String getKK() {
		return KK;
	}

	public void setKK(String kK) {
		KK = kK;
	}

	public boolean isAdmin() {
		return Admin;
	}

	public void setAdmin(boolean admin) {
		Admin = admin;
	}

}
