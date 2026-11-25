package com.example.banque.restAPI;

import com.example.banque.model.TypeProduit;
import com.example.banque.service.TypeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banque_rest/TypeProduit")
public class TypeProduitRestController {

    private final TypeProduitService typeProduitService;

    @Autowired
    public TypeProduitRestController(TypeProduitService typeProduitService) {
        this.typeProduitService = typeProduitService;
    }

    @GetMapping
    public ResponseEntity<List<TypeProduit>> getAllTypeProduit() {
        List<TypeProduit> liste = typeProduitService.getAllTypeProduit();
        return new ResponseEntity<>(liste, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TypeProduit> getTypeProduitById(
            @PathVariable("id") long idTypeProduit) {
        TypeProduit tp = typeProduitService.getTypeProduitById(idTypeProduit);
        return new ResponseEntity<>(tp, HttpStatus.OK);
    }
}
