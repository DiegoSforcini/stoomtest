package com.diegosforcini.stoomtest;

import com.diegosforcini.stoomtest.model.Address;
import com.diegosforcini.stoomtest.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressResource {
    private final AddressService addressService;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAll() {
        List<Address> addresses = addressService.getAllAddress();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Address> get(@PathVariable("id") Long id) {
        Address address = addressService.getAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Address> create(@RequestBody Address address) {
        Address newAddress = addressService.createAddress(address);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Address> update(@RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable("id") Long id) {
        addressService.deleteAddressById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
