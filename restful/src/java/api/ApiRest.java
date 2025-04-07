package api;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.beans.ProductosBean;
import modelo.pojos.Producto;

@Path("producto")
public class ApiRest {

    @Context
    private UriInfo context;

    public ApiRest() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJSON() {
        Gson json = new Gson();
        ProductosBean productosBean = new ProductosBean();
        productosBean.listarProductos();
        String response = json.toJson(productosBean.getListaProductos());
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postJSON(String content) {
        Gson json = new Gson();
        Producto producto = json.fromJson(content, Producto.class);
        ProductosBean productosBean = new ProductosBean();
        productosBean.setProducto(producto);
        productosBean.agregarProducto();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJSON(String content) {
        Gson json = new Gson();
        Producto producto = json.fromJson(content, Producto.class);
        ProductosBean productosBean = new ProductosBean();
        productosBean.setProducto(producto);
        productosBean.modificarProducto();
    }

    @GET
    @Path("/buscarProducto/{clave}")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarProducto(@PathParam("clave") String clave) {

        ProductosBean productosBean = new ProductosBean();
        Producto producto = new Producto();
        producto.setClave(clave);

        Gson json = new Gson();
        String response = json.toJson(productosBean.getProducto());
        return response;
    }
}
