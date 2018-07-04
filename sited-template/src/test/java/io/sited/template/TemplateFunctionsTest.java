package io.sited.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author chi
 */
class TemplateFunctionsTest {
    @Test
    void format() {
        TemplateFunctions templateFunctions = new TemplateFunctions();
        assertEquals("", templateFunctions.format(1, "##0.0##"));
    }
}