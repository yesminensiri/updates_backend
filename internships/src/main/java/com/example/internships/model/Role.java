package com.example.internships.model;

public enum Role {
    ADMIN("ADMIN"),
    ENTREPRISE("ENTREPRISE"),
    ETUDIANT("ETUDIANT");


    private final String value;

    // MODIFIER le constructeur
    Role(String value) {
        this.value = value.toUpperCase(); // Garantit la cohérence case
    }

    public String getAuthority() {
        return "ROLE_" + value;
    }
}
