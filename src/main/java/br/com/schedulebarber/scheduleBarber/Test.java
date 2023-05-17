package br.com.schedulebarber.scheduleBarber;

import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Service.SchedulingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.schedulebarber.scheduleBarber.Util.RemovedAcent.removerAcento;

public class Test {
    public static void main(String[] args) {

        LocalDateTime dateTime1 = LocalDateTime.now();
        LocalDateTime dateTime2 = LocalDateTime.of(2023,04,07, 14,45);

        System.out.println(dateTime1.isAfter(dateTime2));


    }
}
