package com.example.demo.impl;

import com.example.demo.config.CustomDataSourceConfig;
import com.example.demo.service.impl.MutantServiceImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.PropertyVetoException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MutantServiceImplTest {

    @InjectMocks
    MutantServiceImpl mutantServiceImpl;

    @Mock
    CustomDataSourceConfig customDataSourceConfig;

    @Before
    public void setUp() throws PropertyVetoException {
        Mockito.when(customDataSourceConfig.dataSource()).thenReturn(new ComboPooledDataSource());
    }

    @Test
    public void isNoMutantTest() {
        String[] dna = {
                "ACAATATT",
                "CAGTGCTT",
                "TTATGTTA",
                "AGACGGCT",
                "CCCTTATT",
                "TTACGGTA",
                "TCACTGCT",
                "TCACTGTT"
        };

         assertFalse(mutantServiceImpl.isMutant(dna));
    }

    @Test
    public void isMutantTest1() {
        String[] dna = {
                "AAAATTTT",
                "CAGTGCTT",
                "TTATGTTA",
                "AGACGGCT",
                "CCCTTATT",
                "TTACGGTA",
                "TCACTGCT",
                "TCACTGTT"
        };

        assertTrue(mutantServiceImpl.isMutant(dna));
    }

    @Test
    public void isMutantTest2() {
        String[] dna = {
                "ACAATGTT",
                "CAGTGCTT",
                "TTATGTTA",
                "AGAAGGCT",
                "CCCTTATT",
                "TTCCGGTA",
                "TCACTGCT",
                "TCACCGTT"
        };

        assertTrue(mutantServiceImpl.isMutant(dna));
    }
}
