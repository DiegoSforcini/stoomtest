package com.diegosforcini.stoomtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diegosforcini.stoomtest.model.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    void deleteAddressById(Long id);

    Optional<Address> getAddressById(Long id);
}
