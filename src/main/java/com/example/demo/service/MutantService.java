package com.example.demo.service;

import com.example.demo.model.StatsDTO;

/**
 * @author Hernán Iannello
 */

public interface MutantService {

    /**
     * Determina si un dna pertenece a un humano o a un mutante
     * @param dna
     * @return true si el dna pertenece a un mutante
     */
    boolean isMutant(String[] dna);

    /**
     * Calcula las estadísticas de todos los dnas analizados
     * @return StatsDTO con los datos
     */
    StatsDTO getStats();
}
