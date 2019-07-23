package com.rg.backendclientapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rg.backendclientapp.dao.ClientRepository;
import com.rg.backendclientapp.entity.Clients;

@Service
public class ClientService implements IClient{

	@Autowired
	ClientRepository clientRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Clients> allClient() {
		List<Clients> clientListe=clientRepository.findAll();
		
		return clientListe;
	}


	@Override
	@Transactional
	public Clients EnregistreClient(Clients client) {
		// TODO Auto-generated method stub
		return clientRepository.save(client);
	}

	@Override
	@Transactional
	public void suprimerClient(Long id) {
		clientRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Clients DetailClient(Long id) {
		// TODO Auto-generated method stub
		return clientRepository.findById(id).orElse(null);
	}
 
	

}
