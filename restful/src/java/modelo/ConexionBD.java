package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static Connection conexion; //null

    public static boolean establecerConexionBD(){
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.
                   getConnection("jdbc:postgresql://localhost:5432/bdescuela",
                           "postgres", "root");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en conexión:" + e);
            return false;
        }
        return true;
    }
    
    public static void cerrarConexionBD(){
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println("No se pudo cerrar la conexión");
        }
    }
    
    public static Connection getConexion() {
        return conexion;
    }
}