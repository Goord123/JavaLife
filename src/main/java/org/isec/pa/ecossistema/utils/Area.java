package org.isec.pa.ecossistema.utils;

import java.io.Serializable;

public record Area(double x1, double x2,
                   double y1, double y2) implements Serializable {
}
