package br.com.schedulebarber.scheduleBarber.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptUtils {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String senha) {
        return passwordEncoder.encode(senha);
    }

    public static boolean matches(String senha, String hashedSenha) {
        return passwordEncoder.matches(senha, hashedSenha);
    }
}
