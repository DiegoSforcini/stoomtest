package com.diegosforcini.stoomtest.service;

import com.diegosforcini.stoomtest.exception.AddressNotFoundException;
import com.diegosforcini.stoomtest.model.Address;
import com.diegosforcini.stoomtest.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository.getAddressById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address id " + id + " was not found!"));
    }

    public void deleteAddressById(Long id) {
        addressRepository.deleteAddressById(id);
    }
}
