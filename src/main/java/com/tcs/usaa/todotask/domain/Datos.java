package com.tcs.usaa.todotask.domain;

import java.util.Date;
import java.util.Optional;

public class Datos {

    public static Optional<Task> task001(){
        return Optional.of(new Task(1L,"Primera tarea spring","Tarea no. 1", new Date(),new Date(),"Yes","ON_TIME"));
    }

    public static Optional<Task> task002(){
        return Optional.of(new Task(2L,"Segunda tarea spring","Tarea no. 2", new Date(),new Date(),"No","LATE"));
    }

    public static Optional<Task> task003(){
        return Optional.of(new Task(3L,"Tercera tarea spring","Tarea no. 3", new Date(),new Date(),"No","On_TIME"));
    }
}
