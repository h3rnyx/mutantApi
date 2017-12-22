package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstadisticasDTO {

    private int countMutantDna;
    private int coutHumanDna;
    private float ratio;

    @JsonProperty("count_mutant_dna")
    public int getCountMutantDna() {
        return countMutantDna;
    }

    public void setCountMutantDna(int countMutantDna) {
        this.countMutantDna = countMutantDna;
    }

    @JsonProperty("count_human_dna")
    public int getCoutHumanDna() {
        return coutHumanDna;
    }

    public void setCoutHumanDna(int coutHumanDna) {
        this.coutHumanDna = coutHumanDna;
    }

    @JsonProperty("ratio")
    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
