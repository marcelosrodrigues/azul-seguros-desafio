package test.com.infobase.desafio.azul.utils;

import com.infobase.desafio.azul.entities.embeddables.Endereco;
import com.infobase.desafio.azul.utils.ViaCepUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViaCepUtilsTest {
    private ViaCepUtils cepUtils = new ViaCepUtils();

    @Test
    public void deveBuscarCEP() {

        Endereco endereco = cepUtils.findByCEP("22770235");

        assertNotNull(endereco);
    }

    @Test
    public void naoDeveBuscarCEP() {

        Endereco endereco = cepUtils.findByCEP("0000000");
        assertNull(endereco);
    }

}