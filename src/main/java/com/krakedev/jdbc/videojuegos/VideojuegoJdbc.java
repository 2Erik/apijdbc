package com.krakedev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.krakedev.jdbc.Conexion;
import com.krakedev.videojuegos.entidades.Videojuego;

public class VideojuegoJdbc {

	private static final Logger LOGGER = LogManager.getLogger(VideojuegoJdbc.class);

	public static Videojuego insertar(String codigo, String nombre, String plataforma, double precio,
			boolean disponible, String genero) {
		
		Connection con = null;
		PreparedStatement ps = null;
		String sql = """
				insert into videojuegos (codigo, nombre, plataforma, precio, disponible, genero)
				values (?,?,?,?,?,?)
				""";
		Videojuego videojuego = null;
		
		try {
			con = Conexion.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setString(1, codigo);
			ps.setString(2, nombre);
			ps.setString(3, plataforma);
			ps.setDouble(4, precio);
			ps.setBoolean(5, disponible);
			ps.setString(6, genero);
			
			videojuego = new Videojuego(codigo, nombre, plataforma, precio, disponible, genero);
			
			int filas = ps.executeUpdate();
			LOGGER.info("Filas insertadas: "+filas);
			LOGGER.info("Se agrago el guego");
		} catch (Exception e) {
			LOGGER.error("Error al agregar "+e.getMessage());
		}finally {
			try {
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				LOGGER.error("Error al cerrar la conexion "+e2.getMessage());
			}
		}
		return videojuego;
	}
}
