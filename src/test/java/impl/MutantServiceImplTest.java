package impl;

import com.example.demo.service.MutantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MutantServiceImplTest {

    @Autowired
    MutantService mutantService;

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

         assertFalse(mutantService.isMutant(dna));
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

        assertTrue(mutantService.isMutant(dna));
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

        assertTrue(mutantService.isMutant(dna));
    }
}
