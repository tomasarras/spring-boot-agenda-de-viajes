package edu.isistan.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.isistan.model.Cliente;
import edu.isistan.model.Producto;

@RestController
@RequestMapping("test")
public class ControllerTest {
	final String URL = "http://localhost:8080/";
	
	@GetMapping("/generar-datos")
    public void generarDatos() throws IOException, InterruptedException {
		List<Cliente> clientes = getClientesMock();
		List<Producto> productos = getProductosMock();
		cargarClientes(clientes);
		cargarProductos(productos);
    }
	
	@SuppressWarnings("unchecked")
	private void cargarClientes(List<Cliente> clientes) {
		String url = URL + "/clientes/";
		JSONObject json;
		for (int i = 0; i < clientes.size(); i++) {
			json = new JSONObject();
			json.put("nombre",clientes.get(i).getNombre());
			post(json,url);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void cargarProductos(List<Producto> productos) {
		String url = URL + "/productos/";
		JSONObject json;
		for (int i = 0; i < productos.size(); i++) {
			json = new JSONObject();
			json.put("nombre",productos.get(i).getNombre());
			json.put("precio",productos.get(i).getPrecio());
			json.put("stock",productos.get(i).getStock());
			post(json,url);
		}
		
	}
	
	private List<Cliente> getClientesMock() {
		List <Cliente> clientes = new ArrayList <>();
		try {
		    String fileIn = "src/main/java/edu/isistan/test/mock_clientes.csv";
		    String line = null;
	
		    // Read all lines in from CSV file and add to studentList
		    FileReader fileReader = new FileReader(fileIn);
		    BufferedReader bufferedReader = new BufferedReader(fileReader);
		    
		    boolean primerLinea = true;
		    while ((line = bufferedReader.readLine()) != null) {
		    	if (!primerLinea) {
			        String[] temp = line.split(",");
			        String nombre = temp[0];
			        clientes.add(new Cliente(nombre));
		    	} else {
		    		primerLinea = false;
		    	}
		    }
		    bufferedReader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return clientes;
	}
	
	private List<Producto> getProductosMock() {
		List <Producto> productos = new ArrayList <>();
		try {
		    String fileIn = "src/main/java/edu/isistan/test/mock_productos.csv";
		    String line = null;
	
		    // Read all lines in from CSV file and add to studentList
		    FileReader fileReader = new FileReader(fileIn);
		    BufferedReader bufferedReader = new BufferedReader(fileReader);
		    boolean primerLinea = true;
		    while ((line = bufferedReader.readLine()) != null) {
		    	if (!primerLinea) {
		    		String[] temp = line.split(",");
			        String nombre = temp[0];
			        float precio = Float.parseFloat(temp[1]);
			        int stock = Integer.parseInt(temp[2]);
			        productos.add(new Producto(nombre,precio,stock));
		    	} else {
		    		primerLinea = false;
		    	}
		    }
		    bufferedReader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return productos;
	}
	
	
	private void post(JSONObject json, String path) {
		String http = path;
        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConnection=null;  
        try {  
			URL url = new URL(http);  
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);   
			urlConnection.setRequestMethod("POST");  
			urlConnection.setUseCaches(false);  
			urlConnection.setConnectTimeout(10000);  
			urlConnection.setReadTimeout(10000);  
			urlConnection.setRequestProperty("Content-Type","application/json");   
			
			urlConnection.setRequestProperty("Host", "localhost");
			urlConnection.connect();  
			
			//Create JSONObject here
			OutputStreamWriter out = new   OutputStreamWriter(urlConnection.getOutputStream());
			out.write(json.toString());
			out.close();  

			int HttpResult =urlConnection.getResponseCode();  
			if(HttpResult ==HttpURLConnection.HTTP_CREATED){  
			BufferedReader br = new BufferedReader(new InputStreamReader(  
			urlConnection.getInputStream(),"utf-8"));  
			String line = null;  
			while ((line = br.readLine()) != null) {  
				sb.append(line + "\n");  
			}  
			br.close();  


            }else{  
            	System.out.println(urlConnection.getResponseMessage());  
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
