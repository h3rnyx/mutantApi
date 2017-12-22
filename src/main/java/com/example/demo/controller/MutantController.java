package com.example.demo.controller;

import com.example.demo.model.EstadisticasDTO;
import com.example.demo.model.SecuenciaDnaDTO;
import com.example.demo.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class MutantController {

    @Autowired
    private MutantService mutantService;

    @RequestMapping(value = "/mutant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void isMutantTest(@RequestBody SecuenciaDnaDTO dna, HttpServletResponse response) {
        if(mutantService.isMutant(dna.getDna()))
            response.setStatus(HttpServletResponse.SC_OK);
        else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstadisticasDTO> generateStats(HttpServletResponse response) {
        EstadisticasDTO result = new EstadisticasDTO();
        result.setCountMutantDna(10);
        result.setCoutHumanDna(123);
        result.setRatio(12.21f);
        return ResponseEntity.ok(result);
    }
}
