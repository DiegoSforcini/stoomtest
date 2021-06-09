package com.diegosforcini.stoomtest.service;

import com.diegosforcini.stoomtest.model.Address;
import com.diegosforcini.stoomtest.repository.AddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void getAddressByIdTest() {
        Address address = addressService.getAddressById(9L);
        assertEquals("Americana", address.getCity());
        assertEquals("Apto 165 - Bloco C", address.getComplement());
        assertEquals("Brasil", address.getCountry());
        assertEquals("-22.7232345", address.getLatitude());
        assertEquals("-47.31778509999999", address.getLongitude());
        assertEquals("Cariobinha", address.getNeighbourhood());
        assertEquals("845", address.getNumber());
        assertEquals("SP", address.getState());
        assertEquals("Rua São Sebastião", address.getStreetName());
        assertEquals("13472-400", address.getZipcode());
    }

    @Test
    public void deleteAddressByIdTest() {
        addressService.deleteAddressById(12L);
        Optional<Address> address = addressRepository.getAddressById(12L);
        assertFalse(address.isPresent());
    }

    @Test
    public void updateAddressTest() {
        Optional<Address> address = addressRepository.getAddressById(9L);
        if (address.isPresent()) {
            Address addressToUpdate = address.get();
            addressToUpdate.setCity(addressToUpdate.getCity() + " Updated");
            addressToUpdate.setComplement(addressToUpdate.getComplement() + " Updated");
            addressToUpdate.setCountry(addressToUpdate.getCountry() + " Updated");
            addressToUpdate.setLatitude(addressToUpdate.getLatitude() + " Updated");
            addressToUpdate.setLongitude(addressToUpdate.getLongitude() + " Updated");
            addressToUpdate.setNeighbourhood(addressToUpdate.getNeighbourhood() + " Updated");
            addressToUpdate.setNumber(addressToUpdate.getNumber() + " Updated");
            addressToUpdate.setState(addressToUpdate.getState() + " Updated");
            addressToUpdate.setStreetName(addressToUpdate.getStreetName() + " Updated");
            addressToUpdate.setZipcode(addressToUpdate.getZipcode() + " Updated");

            Address updatedAddress = addressService.updateAddress(addressToUpdate);

            assertEquals(addressToUpdate.getCity(), updatedAddress.getCity());
            assertEquals(addressToUpdate.getComplement(), updatedAddress.getComplement());
            assertEquals(addressToUpdate.getCountry(), updatedAddress.getCountry());
            assertEquals(addressToUpdate.getLatitude(), updatedAddress.getLatitude());
            assertEquals(addressToUpdate.getLongitude(), updatedAddress.getLongitude());
            assertEquals(addressToUpdate.getNeighbourhood(), updatedAddress.getNeighbourhood());
            assertEquals(addressToUpdate.getNumber(), updatedAddress.getNumber());
            assertEquals(addressToUpdate.getState(), updatedAddress.getState());
            assertEquals(addressToUpdate.getStreetName(), updatedAddress.getStreetName());
            assertEquals(addressToUpdate.getZipcode(), updatedAddress.getZipcode());
        }
    }

    @Test
    public void createAddressTest() {
        Address addressToCreate = new Address();
        addressToCreate.setCity("Americana");
        addressToCreate.setComplement("Casa");
        addressToCreate.setCountry("Brasil");
        addressToCreate.setLatitude("-22.722962342636404");
        addressToCreate.setLongitude("-47.317763644893105");
        addressToCreate.setNeighbourhood("São Manoel");
        addressToCreate.setNumber("1006");
        addressToCreate.setState("SP");
        addressToCreate.setStreetName("Rua São Thiago");
        addressToCreate.setZipcode("13472-230");
        Address createdAddress = addressService.createAddress(addressToCreate);

        assertTrue(Objects.nonNull(createdAddress));
    }
}
