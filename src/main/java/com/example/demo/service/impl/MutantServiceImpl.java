package com.example.demo.service.impl;

import com.example.demo.service.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MutantServiceImpl implements MutantService{

    private Logger log = LoggerFactory.getLogger(getClass().getName());

    public boolean isMutant(String[] dna) {
        int cantidadDeSecuenciasPositivas = contarSecuenciasPositivasHorizontales(dna) +
                contarSecuenciasPositivasVerticales(dna) + contarSecuenciasPositivasOblicuas(dna);

        return cantidadDeSecuenciasPositivas > 1 ? true : false;
    }

    private int contarSecuenciasPositivasHorizontales(String[] dna) {
        int cantSecuenciasPositivas = 0;
        for(String secuenciaDna : dna) {
            cantSecuenciasPositivas += contarPositivosPorSecuencia(secuenciaDna);
        }

        log.debug("Secuencias Horizontales:\t" + cantSecuenciasPositivas);
        return cantSecuenciasPositivas;
    }

    private int contarSecuenciasPositivasVerticales(String[] dna) {
        int cantSecuenciasPositivas = 0;
        for(int i=0; i<dna.length; i++) {
            StringBuffer secuenciaAux = new StringBuffer();
            for(int j=0; j<dna.length; j++) {
                secuenciaAux.append(dna[j].charAt(i));
            }
            log.debug("Secuencias Verticales: " + secuenciaAux);
            cantSecuenciasPositivas += contarPositivosPorSecuencia(secuenciaAux.toString());
        }

        log.debug("Secuencias Verticales:\t\t" + cantSecuenciasPositivas);
        return cantSecuenciasPositivas;
    }

    private int contarSecuenciasPositivasOblicuas(String[] dna) {
        int cantSecuenciasPositivas = contarPositivosEnDiagonalesInferirores(dna)
                + contarPositivosEnDiagonalesSuperiores(dna);

        log.debug("Secuencias Oblicuas:\t" + cantSecuenciasPositivas);
        return cantSecuenciasPositivas;
    }

    private int contarPositivosEnDiagonalesInferirores(String[] dna) {
        int cantSecuenciasPositivas = 0;
        for(int pivote=0; pivote<dna.length-3; pivote++) {
            StringBuffer secuenciaAux = new StringBuffer();
            int fila, columna;
            for(fila=pivote, columna=0; fila<dna.length && columna<dna.length; fila++, columna++) {
                secuenciaAux.append(dna[fila].charAt(columna));
            }
            log.debug("Secuencias oblicuas inferiores: " + secuenciaAux);
            cantSecuenciasPositivas += contarPositivosPorSecuencia(secuenciaAux.toString());
        }
        return cantSecuenciasPositivas;
    }

    private int contarPositivosEnDiagonalesSuperiores(String[] dna) {
        int cantSecuenciasPositivas = 0;
        for(int pivote=1; pivote<dna.length-3; pivote++) {
            StringBuffer secuenciaAux = new StringBuffer();
            int fila, columna;
            for(fila=0, columna=pivote; fila<dna.length && columna<dna.length; fila++, columna++) {
                secuenciaAux.append(dna[fila].charAt(columna));
            }
            log.debug("Secuencias oblicuas superiores: " + secuenciaAux);
            cantSecuenciasPositivas += contarPositivosPorSecuencia(secuenciaAux.toString());
        }
        return cantSecuenciasPositivas;
    }

    private int contarPositivosPorSecuencia(String secuenciaDna) {
        int cantPositivos = 0;
        Pattern pattern = Pattern.compile("(AAAA|TTTT|CCCC|GGGG)");
        Matcher matcher = pattern.matcher(secuenciaDna);

        while (matcher.find())
            cantPositivos++;

        return cantPositivos;
    }
}
