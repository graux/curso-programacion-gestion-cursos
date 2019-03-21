package com.palmaactiva.javaformacion.ui.validacion;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class ValidadorNuevaPersona extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        System.out.println("Input: " + input.getName());
        return false;
    }

    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target) {
        return verify(source);
    }

}
