package com.example.banque.restAPI;

import com.example.banque.dataTransferObjects.OperationDto;
import com.example.banque.dataTransferObjects.ProduitBancaireDto;
import com.example.banque.dataTransferObjects.TypeProduitDto;
import com.example.banque.service.MobileBanqueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banque_rest/mobile")
public class MobileBanqueRestController {

    private final MobileBanqueService mobileService;

    public MobileBanqueRestController(MobileBanqueService mobileService) {
        this.mobileService = mobileService;
    }

    @GetMapping("/types-produits")
    public List<TypeProduitDto> getTypesProduits() {
        return mobileService.getTypesProduits();
    }

    @GetMapping("/produits")
    public List<ProduitBancaireDto> getProduits() {
        return mobileService.getProduits();
    }

    @GetMapping("/produits/{numeroCompte}")
    public ProduitBancaireDto getCompte(@PathVariable("numeroCompte") String numeroCompte) {
        return mobileService.getCompte(numeroCompte);
    }

    @GetMapping("/produits/{numeroCompte}/operations")
    public List<OperationDto> getDernieresOperations(@PathVariable("numeroCompte") String numeroCompte) {
        return mobileService.getDernieresOperations(numeroCompte);
    }
}
