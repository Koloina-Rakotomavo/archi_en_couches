package com.example.banque.dataTransferObjects;

import java.math.BigDecimal;

public record VirementResultatDto(String compteSource, String compteDestination,
                                  BigDecimal montant, BigDecimal soldeSource,
                                  BigDecimal soldeDestination) {
}
