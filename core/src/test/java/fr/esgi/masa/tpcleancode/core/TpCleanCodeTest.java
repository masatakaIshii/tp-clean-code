package fr.esgi.masa.tpcleancode.core;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class TpCleanCodeTest {

    TpCleanCode sut;

    @Mock
    ADependency aDependency;

    @Before
    public void setUp() {
        aDependency = Mockito.mock(ADependency.class);
        sut = new TpCleanCode(aDependency);
    }

    @Test
    public void test() {
        var result = sut.test();
        Mockito.verify(aDependency).call();
        Assertions.assertThat(result).isEqualTo("test");
    }
}