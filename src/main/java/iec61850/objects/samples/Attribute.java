package iec61850.objects.samples;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Attribute<T> {
    private T value;
}
