package com.example.demo.model;

public class MutantDAO {

    private long id;
    private String dna;
    private boolean isMutant;

    public MutantDAO(long id, String dna, boolean isMutant) {
        this.id = id;
        this.dna = dna;
        this.isMutant = isMutant;
    }

    public long getId() {
        return id;
    }

    public String getDna() {
        return dna;
    }

    public boolean isMutant() {
        return isMutant;
    }
}
