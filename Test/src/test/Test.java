/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import api.TestApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import modelo.pojos.Producto;

/**
 *
 * @author 5-12
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestApi api = new TestApi();
        Gson json = new Gson();
        // Producto producto = new Producto();
        /*producto.setClave("P001");
        producto.setDescripcion("Computadora");
        producto.setPrecio(1500.0);
        String paramJson = json.toJson(producto);
        api.postJSON(paramJson); */

 /*producto.setClave("P001");
        producto.setDescripcion("Computadora de escritorio");
        producto.setPrecio(2500.0);
        String paramJson = json.toJson(producto);
        api.putJSON(producto); */
        String response = api.getJSON(String.class);

        List<Producto> listaProductos = json.fromJson(response, new TypeToken<List<Producto>>() {
        }.getType());

        for (Producto item : listaProductos) {
            //System.out.println(item.getId_producto());
            System.out.println(item.getClave());
            System.out.println(item.getDescripcion());
            System.out.println(item.getPrecio());
        }

        try {
            String id = "P001";
            URL url = new URL("http://10.0.89.252:8080/restful/webresources/producto/buscarProducto/" + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responsecode = con.getResponseCode();
            if (responsecode != 200) {
                System.out.println("Error: " + responsecode + " " + con.getResponseMessage());
            } else {
                StringBuilder informationstring = new StringBuilder();
                try (Scanner sc = new Scanner(url.openStream())) {
                    while (sc.hasNext()) {
                        informationstring.append(sc.nextLine());
                    }
                }

                String cadenajson = informationstring.toString();
                System.out.println("Valor recibido del GET con parámetros: " + informationstring);

                Producto producto = json.fromJson(cadenajson, Producto.class);
                System.out.println(producto.getClave());
                System.out.println(producto.getDescripcion());
                System.out.println(producto.getPrecio());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
