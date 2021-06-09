package com.diegosforcini.stoomtest.service;

import com.diegosforcini.stoomtest.exception.AddressNotFoundException;
import com.diegosforcini.stoomtest.model.Address;
import com.diegosforcini.stoomtest.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressService {

    private final String GOOGLE_API_KEY = "AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw";

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(Address address) {
        if ((Objects.isNull(address.getLatitude()) || address.getLatitude().isEmpty()) ||
                (Objects.isNull(address.getLongitude()) || address.getLongitude().isEmpty())) {
            getLatLong(address);
        }
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

    @Transactional
    public void deleteAddressById(Long id) {
        addressRepository.deleteAddressById(id);
    }

    private Address getLatLong(Address address) {

        try {
            String stringAddress = new StringBuilder(address.getStreetName())
                    .append("+").append(address.getNumber())
                    .append("+").append(address.getNeighbourhood())
                    .append("+").append(address.getCity())
                    .append("+").append(address.getState()).toString();
            stringAddress = stringAddress.replaceAll(" ", "+");

            URL googleGeocodingUrl = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + stringAddress + "&key=" + GOOGLE_API_KEY);
            HttpURLConnection con = (HttpURLConnection) googleGeocodingUrl.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() != 200) {
                throw new AddressNotFoundException("HTTP error code " + con.getResponseCode() + " with message: " + con.getResponseMessage());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

            JSONTokener jsonTokener = new JSONTokener(br);
            JSONObject json = new JSONObject(jsonTokener);

//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = br.readLine()) != null) sb.append(line);
//            JSONObject json = new JSONObject(sb.toString());

            String status = json.getString("status");
            if (status.equalsIgnoreCase("OK")) {
                JSONArray results = json.getJSONArray("results");

                JSONObject resultsNode = results.getJSONObject(0);
                JSONObject geometry = resultsNode.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                String lat = String.valueOf(location.getBigDecimal("lat"));
                String lng = String.valueOf(location.getBigDecimal("lng"));

                if (Objects.isNull(address.getLatitude()) || address.getLatitude().isEmpty()) address.setLatitude(lat);
                if (Objects.isNull(address.getLongitude()) || address.getLongitude().isEmpty()) address.setLongitude(lng);
            }

            con.disconnect();
        } catch (Exception ex) {
            throw new AddressNotFoundException("Error: " + ex);
        }

        return address;
    }
}
