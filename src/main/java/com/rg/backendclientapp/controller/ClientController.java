package com.rg.backendclientapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rg.backendclientapp.entity.Clients;
import com.rg.backendclientapp.service.ClientService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ClientController {

	Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    ClientService clientService;
    
    @GetMapping("/listeClient")
    public List<Clients> index(){
    	//List<Clients> liste = clientService.allClient();
    	return clientService.allClient();
    }
    
    @GetMapping("/detaiclient/{id}")
    public ResponseEntity<?> detailClient(@PathVariable Long id) {
    	Clients client=null;
    	Map<String, Object> response = new HashMap<>();
    	try {
    		client=clientService.DetailClient(id);	
		} catch (DataAccessException e) {
			response.put("message","Erreur de consultation");
			response.put("error",e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
    
    	
    	if(client==null) {
    		response.put("message", "Le client avec id: ".concat(id.toString().concat(" n'existe pas dans notre base de donnée")));
    		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
    	
    	}
    	return new ResponseEntity<Clients>(client,HttpStatus.OK) ;
    }
    
    @PostMapping("/saveClients")
    public ResponseEntity<?> create(@RequestBody(required = true) @Valid Clients client,BindingResult bindinResul) {
    	Clients newClient=null;
    	Map<String, Object> response = new HashMap<>();
    	
    	if(bindinResul.hasErrors()) {
    		/*List<String> errors=new ArrayList<>();
    		for(FieldError err:bindinResul.getFieldErrors()) {
    			errors.add(err.getDefaultMessage());
    		}*/
    		//recuperation de erreur genere grace au model
    		List<String> errors =bindinResul.getFieldErrors()
    				.stream()
    				.map(err -> err.getDefaultMessage())
    				.collect(Collectors.toList());
    		
    		response.put("error",errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
    	}
    	
    	
    	 try {
			newClient=clientService.EnregistreClient(client);
		} catch (DataAccessException e) {
			
			response.put("message","Une erreur c'est produit lors de l'enregistrement");
			response.put("error",e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		  response.put("message ", "le client à été enregistré");
		  response.put("client", newClient);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;
    	
    }
    
    @PutMapping("/updateClient/{id}")
    public ResponseEntity<?> updateClient(@RequestBody(required = true) @Valid Clients client,BindingResult bindinResul,@PathVariable Long id) {
		logger.info("An INFO Message "+id);
		Clients clientActuel;
		Map<String, Object> response = new HashMap<>();
		
		if(bindinResul.hasErrors()) {
    		/*List<String> errors=new ArrayList<>();
    		for(FieldError err:bindinResul.getFieldErrors()) {
    			errors.add(err.getDefaultMessage());
    		}*/
			List<String> errors =bindinResul.getFieldErrors()
    				.stream()
    				.map(err -> err.getDefaultMessage())
    				.collect(Collectors.toList());
    		response.put("error",errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
    	}
    	
		
		try {
			clientActuel=clientService.DetailClient(id);
		   	clientActuel.setName(client.getName());
	    	clientActuel.setEmail(client.getEmail());
	    	clientActuel.setTelephone(client.getTelephone());
	    	clientActuel.setUsername(client.getUsername());
	    	clientService.EnregistreClient(clientActuel);
		} catch (DataAccessException e) {
			response.put("message","Une erreur de de modification");
			response.put("error",e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
 
		response.put("Message ", "Le client a été modifie");
		  response.put("client", clientActuel);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;
    }
    
    @GetMapping("/deleteClient/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void suprimerClient(@PathVariable Long id) {
    	clientService.suprimerClient(id);
    }
}
