package edu.isistan.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.isistan.model.Cliente;
import edu.isistan.model.Compra;
import edu.isistan.model.Producto;
import edu.isistan.repository.ClienteRepository;
import edu.isistan.repository.ProductoRepository;

@RestController
@RequestMapping("test")
public class ControllerTest {
	private final String URL = "http://localhost:8080/";
	private final int MAX_PRODUCTOS = 3;
	@Autowired
	private ClienteRepository repositoryCliente;

	@Autowired
	private ProductoRepository repositoryProducto;
	
	@GetMapping("/generar-datos")
    public void generarDatos() throws IOException, InterruptedException {
		List<Cliente> clientes = getClientesMock();
		List<Producto> productos = getProductosMock();
		cargarClientes(clientes);
		cargarProductos(productos);
		List<Compra> compras = getComprasMock();
		cargarCompras(compras);
		/*clientes = repositoryCliente.findAll();
		productos = repositoryProducto.findAll();
		generarCSVCompras(clientes,productos);*/
    }
	
	@SuppressWarnings("unused")
	private void generarCSVCompras(List<Cliente> clientes, List<Producto> productos) {
		int cantidadDeDiasAGenerar = 13;// desde el dia de inicio se crean 13 dias
		int cantidadDeComprasxDiaAGenerar = 30;// cuantos clientes hacen compras en un dia
		int cantidadComprasPorClienteMaximas = 7; // cantidad de productos diferentes por cliente
		List<Compra> compras = new ArrayList<Compra>();
		
		int diaInicio = 18;
		LocalDate fechaInicio;
		for (int i = 0; i < cantidadDeDiasAGenerar; i++) {
			fechaInicio = LocalDate.parse("2020-10-" + (diaInicio + i));
			compras.addAll(generarXCompras(clientes,
											productos,
											cantidadDeComprasxDiaAGenerar,
											fechaInicio,
											cantidadComprasPorClienteMaximas));
		}
		
		crearCSVCompras(compras);
	}
	
	private List<Compra> generarXCompras(List<Cliente> clientes,List<Producto> productos,int cantidadCompras, LocalDate fecha, int cantidadComprasPorClienteMaximas) {
		List<Compra> compras = new ArrayList<Compra>();
		List<Cliente> clientesYaElegidos = new ArrayList<Cliente>();
		List<Producto> productosYaElegidos = new ArrayList<Producto>();
		int randomInt;
		int cantidadComprasPorCliente;
		int cantidadUnidades;
		Cliente cliente;
		Producto producto;
		for (int i = 0; i < cantidadCompras; i++) {
			productosYaElegidos = new ArrayList<Producto>();
			randomInt = (int) Math.floor(Math.random() * clientes.size());
			cliente = clientes.get(randomInt);
			while (clientesYaElegidos.contains(cliente)) {
				randomInt = (int) Math.floor(Math.random() * clientes.size());
				cliente = clientes.get(randomInt);				
			}
			
			cantidadComprasPorCliente = (int) Math.floor(Math.random() * cantidadComprasPorClienteMaximas);
			for (int j = 0; j < cantidadComprasPorCliente; j++) {
				cantidadUnidades = (int) Math.floor(Math.random() * MAX_PRODUCTOS) +1;
				randomInt = (int) Math.floor(Math.random() * productos.size());
				producto = productos.get(randomInt);
				while (productosYaElegidos.contains(producto)) {
					randomInt = (int) Math.floor(Math.random() * productos.size());
					producto = productos.get(randomInt);
				}
				productosYaElegidos.add(producto);
				for (int k = 0; k < cantidadUnidades; k++) {
					compras.add(new Compra(cliente,producto,fecha));
				}
				
			}
		}
		
		return compras;
	}
	
	private void crearCSVCompras(List<Compra> compras) {
		try {
			FileWriter csvWriter = new FileWriter("src/main/java/edu/isistan/test/mock_compras.csv");
			csvWriter.append("idCliente");
			csvWriter.append(",");
			csvWriter.append("idProducto");
			csvWriter.append(",");
			csvWriter.append("fecha");
			csvWriter.append("\n");
	
			for (Compra compra : compras) {
				csvWriter.append(compra.getCliente().getId() + ",");
				csvWriter.append(compra.getProducto().getId() + ",");
				csvWriter.append(compra.getFecha().toString());
			    csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarCompras(List<Compra> compras) {
		String url = URL + "compras/clientes/";
		JSONObject json = new JSONObject();
		Compra c;
		for (int i = 0; i < compras.size(); i++) {
			c = compras.get(i);
			post(json,url + c.getCliente().getId() +  "/productos/" + c.getProducto().getId() + "/",true);
		}
		
	}

	private List<Compra> getComprasMock() {
		List <Compra> compras = new ArrayList <>();
		try {
		    String fileIn = "src/main/java/edu/isistan/test/mock_compras.csv";
		    String line = null;
	
		    // Read all lines in from CSV file and add to studentList
		    FileReader fileReader = new FileReader(fileIn);
		    BufferedReader bufferedReader = new BufferedReader(fileReader);
		    
		    boolean primerLinea = true;
		    Cliente c;
		    Producto p;
		    LocalDate fecha;
		    int id;
		    while ((line = bufferedReader.readLine()) != null) {
		    	if (!primerLinea) {
			        String[] temp = line.split(",");
			        id = Integer.parseInt(temp[0]);
			        c = repositoryCliente.findById(id).get();
			        id = Integer.parseInt(temp[1]);
			        p = repositoryProducto.findById(id).get();
			        fecha = LocalDate.parse(temp[2]);
			        compras.add(new Compra(c,p,fecha));
		    	} else {
		    		primerLinea = false;
		    	}
		    }
		    bufferedReader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return compras;
	}

	@SuppressWarnings("unchecked")
	private void cargarClientes(List<Cliente> clientes) {
		String url = URL + "clientes/";
		JSONObject json;
		for (int i = 0; i < clientes.size(); i++) {
			json = new JSONObject();
			json.put("nombre",clientes.get(i).getNombre());
			post(json,url,false);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void cargarProductos(List<Producto> productos) {
		String url = URL + "productos/";
		JSONObject json;
		for (int i = 0; i < productos.size(); i++) {
			json = new JSONObject();
			json.put("nombre",productos.get(i).getNombre());
			json.put("precio",productos.get(i).getPrecio());
			json.put("stock",productos.get(i).getStock());
			post(json,url,false);
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
	
	
	private void post(JSONObject json, String path,boolean a) {
		if (a) {
			int i = 0;
		}
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
