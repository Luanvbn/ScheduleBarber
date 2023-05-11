package br.com.schedulebarber.scheduleBarber.Util;

import java.text.Normalizer;

public class RemovedAcent {

    public static String removerAcento(String palavra){
        return Normalizer.normalize(palavra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
