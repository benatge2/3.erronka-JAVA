package usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.DBconnect;

public abstract class Pertsona {
	private String NAN;
	private String Name;
	private String Apellido;
	private String Mail;
	private String Pass;

	public Pertsona() {

	}

	public Pertsona(String nAN, String name, String apellido, String mail, String pass) {
		this.NAN = nAN;
		this.Name = name;
		this.Apellido = apellido;
		this.Mail = mail;
		this.Pass = pass;
	}

	public void informazioaJaso(String tabla, String nan) {
		String Query = "SELECT * FROM "+tabla+" WHERE nan = "+nan;
		try (DBconnect obJConnection = new DBconnect()){
			Connection connection = obJConnection.getConnection();
			PreparedStatement cs = connection.prepareStatement(Query);
			ResultSet rs = cs.executeQuery(Query);

			while (rs.next()) {
               this.NAN = rs.getString("nan");
               this.Name = rs.getString("nombre");
               this.Apellido = rs.getString("apellido");
               this.Mail = rs.getString("email");
               this.Pass = rs.getString("contraseña");
            }
			rs.close();
            cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void informazioaGorde(String tabla) {
		String updateQuery = "UPDATE " + tabla + " SET nan = ?, nombre = ?, apellido = ?, email = ?, contraseña = ? WHERE nan = '"+this.NAN+"'";
		String queryCheck = "SELECT COUNT(*) FROM "+tabla+" WHERE nan = ?";
		String insertQuery = "INSERT INTO "+tabla+" (nan, nombre, apellido, email, contraseña) VALUES (?, ?, ?, ?, ?)";

		try (DBconnect obJConnection = new DBconnect()) {
			Connection connection = obJConnection.getConnection();
			PreparedStatement checkStatement = connection.prepareStatement(queryCheck);
			checkStatement.setString(1, this.NAN);
			var result = checkStatement.executeQuery();
			result.next();
	        int count = result.getInt(1);

	        if(count > 0) {
	        	PreparedStatement ps = connection.prepareStatement(updateQuery);
				ps.setString(1,this.NAN);
				ps.setString(2,this.Name);
				ps.setString(3,this.Apellido);
				ps.setString(4,this.Mail);
				ps.setString(5,this.Pass);

				int rowsUpdated = ps.executeUpdate();
				if (rowsUpdated > 0) {
			            System.out.println("Fila actualizada exitosamente.");
			        } else {
			            System.out.println("No se pudo actualizar la fila.");
			        }
				ps.close();
				connection.close();
				obJConnection.close();
	        }else {
	        	PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
	        	insertStatement.setString(1,this.NAN);
	        	insertStatement.setString(2,this.Name);
	        	insertStatement.setString(3,this.Apellido);
	        	insertStatement.setString(4,this.Mail);
	        	insertStatement.setString(5,this.Pass);

				int rowsUpdated = insertStatement.executeUpdate();
				if (rowsUpdated > 0) {
			            System.out.println("Fila actualizada exitosamente.");
			        } else {
			            System.out.println("No se pudo actualizar la fila.");
			        }
				insertStatement.close();
				connection.close();
				obJConnection.close();
	        }



		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getNAN() {
		return NAN;
	}

	public void setNAN(String nAN) {
		NAN = nAN;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public String getPass() {
		return Pass;
	}

	public void setPass(String pass) {
		Pass = pass;
	}

}
