package com.techelevator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.BreweryDao;
import com.techelevator.model.Beer;
import com.techelevator.model.Brewery;

@RestController
@CrossOrigin
public class BreweryController {
    @Autowired
    BreweryDao brewery;

    @GetMapping(path = {"/breweries"})
    public List<Brewery> getAllBreweries() {
        List<Brewery> breweryList = brewery.getAllBreweries();
        return breweryList;
    }

    @DeleteMapping(path = "/breweries/{id}")
    public void deleteBrewery(@PathVariable int id) {
        brewery.deleteBrewery(id);
    }

    @PutMapping(path = "/updateBrewery")
    @ResponseStatus(HttpStatus.OK)
    public void updateBrewery(@RequestBody Brewery breweryToUpdate) {
        brewery.updateBrewery(breweryToUpdate);
    }

    @PostMapping(path = {"/breweries"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addBrewery(@RequestBody Brewery newBrewery) {
        brewery.createBrewery(newBrewery);
    }

    @GetMapping(path = "/breweries/glutenfree")
    public List<Brewery> getAllBreweriesWithGFBeer() {
        List<Brewery> glutenFreeBeers = brewery.getAllBreweriesWithGFBeer();
        return glutenFreeBeers;
    }

    @GetMapping(path = "/breweries/{breweryId}/beers")
    public List<Beer> getBeersByBreweryId(@PathVariable int breweryId) {
        List<Beer> beerList = brewery.getBeersByBreweryId(breweryId);
        return beerList;
    }
}