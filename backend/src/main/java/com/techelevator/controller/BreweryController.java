package com.techelevator.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.techelevator.dao.JdbcBreweryDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Food;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.dao.BreweryDao;
import com.techelevator.model.Beer;
import com.techelevator.model.Brewery;

@RestController
@CrossOrigin
public class BreweryController {

    private BreweryDao breweryDao;
    @Autowired
    private UserDao userDao;

    public BreweryController (BreweryDao breweryDao) {
        this.breweryDao = breweryDao;
    }

    @GetMapping(path = {"/breweries"})
    public List<Brewery> getAllBreweries() {
        List<Brewery> breweryList = breweryDao.getAllBreweries();
        return breweryList;
    }

    @GetMapping(path = "account/breweries/{id}")
    @PreAuthorize("isAuthenticated()")
    public Brewery getBreweryByIdLogged(@PathVariable int id, Principal principal) {
        return breweryDao.getBreweryByIdLogged(id, getId(principal));
    }

    @GetMapping(path = "/breweries/{id}")
    @PreAuthorize("permitAll()")
    public Brewery getBreweryById(@PathVariable int id) {
        return breweryDao.getBreweryById(id);
    }

    @GetMapping(path = "/account/breweries")
    @PreAuthorize("hasRole('ROLE_BREWER')")
    public List<Brewery> getBreweryByUserId(Principal principal){
        return breweryDao.getBreweryByUserId(getId(principal));
    }

    @DeleteMapping(path = "account/breweries/{id}")
    @PreAuthorize("hasRole('ROLE_BREWER')")
    public void deleteBrewery(@PathVariable int id) {
        breweryDao.deleteBrewery(id);
    }

    @PutMapping(path = "account/breweries/{breweryId}/edit")
    @ResponseStatus(HttpStatus.OK)
    public void updateBrewery(@RequestBody Brewery breweryToUpdate) {
        breweryDao.updateBrewery(breweryToUpdate);
    }

    @PostMapping(path = {"/account/admin/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public long addBrewery(@RequestBody Brewery newBrewery) {
        return breweryDao.createBrewery(newBrewery);
    }

    @GetMapping(path = "/breweries/glutenfree")
    public List<Brewery> getAllBreweriesWithGFBeer() {
        List<Brewery> glutenFreeBeers = breweryDao.getAllBreweriesWithGFBeer();
        return glutenFreeBeers;
    }

    @GetMapping(path = "/breweries/{breweryId}/edit")
    public List<Food> getFoodList(){
        return breweryDao.getFoodList();
    }

    @GetMapping(path="account/admin/add")
    public List<User> getBrewers(){
        return userDao.findAllBrewer();
    }

    @PutMapping(path="account/admin/assign")
    @ResponseStatus(HttpStatus.OK)
    public void updateBrewer(@RequestBody Brewery brewery){
        breweryDao.updateBrewer(brewery);
    }

    private long getId(Principal principal){
        if(principal == null){
            return 0L;
        }
        return userDao.findIdByUsername(principal.getName());
    }


}
