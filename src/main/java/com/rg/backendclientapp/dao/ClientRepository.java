package com.rg.backendclientapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rg.backendclientapp.entity.Clients;


@Repository
public interface ClientRepository extends JpaRepository<Clients, Long> {

}
