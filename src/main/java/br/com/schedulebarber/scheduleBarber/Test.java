package br.com.schedulebarber.scheduleBarber;

import br.com.schedulebarber.scheduleBarber.Model.Access;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Model.Sex;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        Client cliente = new Client();
        cliente.setSex(Sex.MASCULINO);
        cliente.setBirthday(LocalDate.now());
        Access acesso = new Access();
        acesso.setEmail("Test@gmail");
        acesso.setPassword("Tt");
        acesso.setRole("ADMIN");

        cliente.setAccess(acesso);

        System.out.println(cliente.toString());
    }
}
