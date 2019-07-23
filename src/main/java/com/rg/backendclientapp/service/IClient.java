package com.rg.backendclientapp.service;

import java.util.Date;
import java.util.List;
import com.rg.backendclientapp.entity.Clients;
public interface IClient {
	
   public List<Clients> allClient();
   public Clients DetailClient(Long id);
   public Clients EnregistreClient(Clients client);
   public void suprimerClient(Long id);
}
