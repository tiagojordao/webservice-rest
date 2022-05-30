package com.example.server.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("company")
public class Company {
    private String name = "T&N`s Linhas Aéreas";
    private ArrayList<Flight> flights;
    private Flight flights1 = new Flight("ABC123", "NATAL", "SAO PAULO", "20-05-2021", 182, true);
    private Flight flights2 = new Flight("ASD133", "JOAO PESSOA", "RIO DE JANEIRO", "21-06-2021", 150, false);
    private Flight flights3 = new Flight("DEC113", "NATAL", "MOSSORO", "15-01-2022", 50, true);

    public Company() {
        flights = new ArrayList<>();

        flights.add(flights1);
        flights.add(flights2);
        flights.add(flights3);
    }

    @GetMapping("name")
    public String getName() {
        return String.format(this.name);
    }

    @GetMapping("flights")
    public ArrayList<Flight> getFlights() {
        return flights;
    }

    @GetMapping("flight/{origin}/{destination}")
    public Flight getByOriginAndDestination(@PathVariable(value = "origin") String origin, @PathVariable(value = "destination") String destination) {
        for (Flight f : flights) {
            if(origin.equals(f.getLeaving_from())  && destination.equals(f.getGoing_to())){
                return f;
            }
        }
        return null;
    }

//  Req sample: http://127.0.0.1:8080/company/reserve/DEC113/15-01-2022/true
    @PostMapping("reserve/{flightcode}/{date}/{isdirect}")
    public String reserveFlight(@PathVariable String flightcode, @PathVariable String date, @PathVariable boolean isdirect) {
        for (Flight f : flights) {
            if(flightcode.equals(f.getCode()) && date.equals(f.getLeaving_date()) && isdirect == f.getDirect_flight() && f.getFlight_capacity() > 0) {
                f.decreaseFlightCapacity();
                return String.format("Reserva Realziada. Nova capacidade do Voo: " + f.getFlight_capacity());
            }
        }
        return String.format("Voo não encontrado!");
    }

}
