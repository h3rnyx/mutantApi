package com.example.demo.service.impl;

import com.example.demo.model.StatsDTO;
import com.example.demo.service.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MutantServiceImpl implements MutantService{

    private Logger log = LoggerFactory.getLogger(getClass().getName());
    static final String INSERT_STATEMENT = "INSERT INTO dna_mutantes(id, dna, isMutant) VALUES(null, ?, ?);";
    static final String SELECT_STATEMENT = "SELECT id, dna, isMutant FROM dna_mutantes;";

    public boolean isMutant(String[] dna) {
        int cantidadDeSecuenciasPositivas = contarSecuenciasPositivasHorizontales(dna) +
                contarSecuenciasPositivasVerticales(dna) + contarSecuenciasPositivasOblicuas(dna);

        boolean isMutant = cantidadDeSecuenciasPositivas > 1 ? true : false;
        //jdbcTemplate.update(INSERT_STATEMENT, dna, isMutant);
        return isMutant;
    }

    //Lee la matriz por filas
    private int contarSecuenciasPositivasHorizontales(String[] dna) {
        int cantSecuenciasPositivas = 0;
        for(String secuenciaDna : dna) {
            cantSecuenciasPositivas += contarPositivosPorSecuencia(secuenciaDna);
        }

        log.debug("Secuencias Horizontales:\t" + cantSecuenciasPositivas);
        return cantSecuenciasPositivas;
    }

    //Lee la matriz por columnas
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

    //Lee la matriz oblicuamente separando en dos el recorido
    private int contarSecuenciasPositivasOblicuas(String[] dna) {
        int cantSecuenciasPositivas = contarPositivosEnDiagonalesInferirores(dna)
                + contarPositivosEnDiagonalesSuperiores(dna);

        log.debug("Secuencias Oblicuas:\t" + cantSecuenciasPositivas);
        return cantSecuenciasPositivas;
    }

    //Recorre la diagonal principal y las inferiores
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

    //Recorre las diagonales superiores a partir de la diagonal principal
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

    //Matchea positivos por string
    private int contarPositivosPorSecuencia(String secuenciaDna) {
        int cantPositivos = 0;
        Pattern pattern = Pattern.compile("(AAAA|TTTT|CCCC|GGGG)");
        Matcher matcher = pattern.matcher(secuenciaDna);

        while (matcher.find())
            cantPositivos++;

        return cantPositivos;
    }

    public StatsDTO getStats() {
        long mutantsQty = 0L, humansQty = 0L, dnaQty = 0L;
        String url = getURL();
        log.debug("Conectando a: " + url);
        try {
            Connection conn = DriverManager.getConnection(url);
            //Obtengo todos los dna's de la base de datos
            try (ResultSet rs = conn.prepareStatement(SELECT_STATEMENT).executeQuery()) {
                //Cuento mutantes y humanos
                while (rs.next()) {
                    dnaQty++;
                    if(rs.getBoolean("isMutant")) {
                        mutantsQty++;
                    } else {
                        humansQty++;
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        float average = dnaQty > 0 ? 0f : mutantsQty/dnaQty;
        log.debug("Mutants: " + mutantsQty, " - Humans: " + humansQty);
        return new StatsDTO(mutantsQty, humansQty, average);
    }

    private String getURL() {
        if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            try {
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                log.error("Error cargando Google JDBC Driver \t| " + e);
            }
            return System.getProperty("ae-cloudsql.cloudsql-database-url");
        } else {
            return System.getProperty("ae-cloudsql.local-database-url");
        }
    }
}