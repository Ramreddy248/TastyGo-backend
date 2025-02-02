package com.ram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
