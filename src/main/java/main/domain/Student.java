package main.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HG
 */
@Data
public class Student implements Serializable {
    String name;
    String six;
    String doThing;
}
