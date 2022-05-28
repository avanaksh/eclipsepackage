package com.iasri.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

@Path("/products")
public class ProductResource {
 private ProductDAO dao=ProductDAO.getInstance();
 
 @GET
 @Produces(MediaType.APPLICATION_JSON)
 public List<Product> list(){
	 return dao.getall();
 }
 @GET
 @Path("{id}")
 @Produces(MediaType.APPLICATION_JSON)
 public ResponseMessage get(@PathParam("id") int id){
	 ResponseMessage rm=new ResponseMessage();
	 Product product=dao.get(id);
	 if(product!=null){
				 rm.setStatus(true);
				 rm.setMessage("Product found with id : "+id);//Response.Ok(product, MediaType.APPLICATION_JSON).build();
	 }else{
		// return Response.status(Response.Status.).build();
		 rm.setStatus(false);
		 rm.setMessage("Product found with id : "+id+ " not found !");
	 }
	 return rm;
 }
 
 @POST
 @Consumes(MediaType.APPLICATION_JSON)
 public ResponseMessage add(Product prod) throws URISyntaxException{
	 ResponseMessage rm=new ResponseMessage();
	 int newProductId=dao.add(prod);
	 URI uri=new URI("/MyWebsites/rest/products/"+newProductId);
	 if(newProductId!=0){
		 rm.setStatus(true);
		 rm.setMessage("Product added");
	 }else{
		 rm.setStatus(false);
		 rm.setMessage("Product not added");
	 }
	return rm;
 }
 @PUT
 @Consumes(MediaType.APPLICATION_JSON)
 public ResponseMessage update(@PathParam("id") int id,Product prod) throws URISyntaxException{
	 ResponseMessage rm=new ResponseMessage();
	 prod.setId(id);	 
	 if(dao.update(prod)){
		 rm.setStatus(true);
		 rm.setMessage("Product updated");
		 
	 }else{
		 rm.setStatus(false);
		 rm.setMessage("Product not updated");
	 }
	return rm;
 }
 @DELETE
 @Path("{id}")
 public ResponseMessage delete(@PathParam("id") int id){
	 ResponseMessage rm=new ResponseMessage();
	 if(dao.delete(id)){
		 rm.setStatus(true);
		 rm.setMessage("Product deleted");
	 }else{
		 rm.setStatus(false);
		 rm.setMessage("Product not deleted");
	 }
	 return rm;
 }
}
