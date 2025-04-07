package modelo.beans;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.ConexionBD;
import modelo.pojos.Producto;

@ManagedBean
@ViewScoped
public class ProductosBean {

    private Producto producto = new Producto();
    private List<Producto> listaProducto = new ArrayList<>();

    public boolean agregarProducto() {
        boolean estado = true;
        if (ConexionBD.establecerConexionBD()) {
            try {
                CallableStatement sentencia
                        = ConexionBD.getConexion().
                                prepareCall("{call catalogos.fn_agregar_producto(?,?,?)}");
                sentencia.setString(1, this.producto.getClave());
                sentencia.setString(2, this.producto.getDescripcion());
                sentencia.setDouble(3, this.producto.getPrecio());

                sentencia.execute();
            } catch (SQLException e) {
                System.out.println("Error SQL:" + e);
                estado = false;
            }
        } else {
            estado = false;
        }

        ConexionBD.cerrarConexionBD();
        return estado;
    }

    public boolean buscarProducto() {
        boolean estado = true;
        if (ConexionBD.establecerConexionBD()) {
            try {
                CallableStatement sentencia
                        = ConexionBD.getConexion().
                                prepareCall("{call catalogos.fn_buscar_producto(?)}");
                sentencia.setString(1, this.producto.getClave());

                ResultSet consulta = sentencia.executeQuery();

                if (consulta.next()) {//Existe al menos un registro
                    //this.alumno.setMatricula(consulta.getString("matricula"));
                    this.producto.setDescripcion(consulta.getString("descripcion"));
                    this.producto.setPrecio(consulta.getDouble("precio"));
                } else { //No existen registros en la consulta
                    System.out.println("No existen registros");
                    estado = false;
                }
            } catch (SQLException e) {
                System.out.println("Error SQL:" + e);
                estado = false;
            }
        } else {
            estado = false;
        }

        ConexionBD.cerrarConexionBD();
        return estado;
    }

    public boolean listarProductos() {
        boolean estado = true;
        if (ConexionBD.establecerConexionBD()) {
            try {
                CallableStatement sentencia
                        = ConexionBD.getConexion().
                                prepareCall("{call catalogos.fn_listar_producto()}");

                ResultSet consulta = sentencia.executeQuery();

                this.listaProducto = new ArrayList<>();

                while (consulta.next()) {//Recorre todos los registros de la consulta
                    Producto item = new Producto();

                    item.setClave(consulta.getString("clave"));
                    item.setDescripcion(consulta.getString("descripcion"));
                    item.setPrecio(consulta.getDouble("precio"));

                    this.listaProducto.add(item);
                }
            } catch (SQLException e) {
                System.out.println("Error SQL:" + e);
                estado = false;
            }
        } else {
            estado = false;
        }

        ConexionBD.cerrarConexionBD();
        return estado;
    }

    public boolean modificarProducto() {
        boolean estado = true;
        if (ConexionBD.establecerConexionBD()) {
            try {
                CallableStatement sentencia
                        = ConexionBD.getConexion().
                                prepareCall("{call catalogos.fn_modificar_producto(?,?,?)}");
                sentencia.setString(1, this.producto.getClave());
                sentencia.setString(2, this.producto.getDescripcion());
                sentencia.setDouble(3, this.producto.getPrecio());
                sentencia.execute();
            } catch (SQLException e) {
                System.out.println("Error SQL:" + e);
                estado = false;
            }
        } else {
            estado = false;
        }

        ConexionBD.cerrarConexionBD();
        return estado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getListaProductos() {
        return listaProducto;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProducto = listaProductos;
    }
}
