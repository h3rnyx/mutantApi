package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatsDTO {

    private long mutantDnaQty;
    private long humanDnaQty;
    private float ratio;

    public StatsDTO(long mutantDnaQty, long humanDnaQty, float ratio) {
        this.mutantDnaQty = mutantDnaQty;
        this.humanDnaQty = humanDnaQty;
        this.ratio = ratio;
    }

    @JsonProperty("count_mutant_dna")
    public long getMutantDnaQty() {
        return mutantDnaQty;
    }

    public void setMutantDnaQty(long mutantDnaQty) {
        this.mutantDnaQty = mutantDnaQty;
    }

    @JsonProperty("count_human_dna")
    public long getHumanDnaQty() {
        return humanDnaQty;
    }

    public void setHumanDnaQty(long humanDnaQty) {
        this.humanDnaQty = humanDnaQty;
    }

    @JsonProperty("ratio")
    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
