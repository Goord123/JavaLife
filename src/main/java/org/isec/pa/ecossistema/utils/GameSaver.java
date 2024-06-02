package org.isec.pa.ecossistema.utils;

import org.isec.pa.ecossistema.model.data.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GameSaver implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public void saveToCSV(Set<IElemento> elementos, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Type,Id,X1,Y1,X2,Y2,Forca,TimesReproduced,SegundosParaReproduzir\n");

            for (IElemento elemento : elementos) {
                if (elemento instanceof Fauna) {
                    Fauna fauna = (Fauna) elemento;
                    writer.append("Fauna,")
                            .append(String.valueOf(fauna.getId())).append(",")
                            .append(String.valueOf(fauna.getArea().x1())).append(",")
                            .append(String.valueOf(fauna.getArea().y1())).append(",")
                            .append(String.valueOf(fauna.getArea().x2())).append(",")
                            .append(String.valueOf(fauna.getArea().y2())).append(",")
                            .append(String.valueOf(fauna.getForca())).append(",")
                            .append(String.valueOf(fauna.getTimesReproduced())).append(",")
                            .append(String.valueOf(fauna.getSegundosParaReproduzir())).append("\n");
                } else if (elemento instanceof Flora) {
                    Flora flora = (Flora) elemento;
                    writer.append("Flora,")
                            .append(String.valueOf(flora.getId())).append(",")
                            .append(String.valueOf(flora.getArea().x1())).append(",")
                            .append(String.valueOf(flora.getArea().y1())).append(",")
                            .append(String.valueOf(flora.getArea().x2())).append(",")
                            .append(String.valueOf(flora.getArea().y2())).append(",")
                            .append(String.valueOf(flora.getForca())).append(",")
                            .append(String.valueOf(flora.getTimesReproduced())).append("\n");
                } else if (elemento instanceof Inanimado) {
                    Inanimado inanimado = (Inanimado) elemento;
                    writer.append("Inanimado,")
                            .append(String.valueOf(inanimado.getId())).append(",")
                            .append(String.valueOf(inanimado.getArea().x1())).append(",")
                            .append(String.valueOf(inanimado.getArea().y1())).append(",")
                            .append(String.valueOf(inanimado.getArea().x2())).append(",")
                            .append(String.valueOf(inanimado.getArea().y2())).append("\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<IElemento> loadFromCSV(Ecossistema ecossistema, String filePath) throws IOException {
        Set<IElemento> elementos = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String type = tokens[0];
                int id = Integer.parseInt(tokens[1]);
                double x1 = Double.parseDouble(tokens[2]);
                double y1 = Double.parseDouble(tokens[3]);
                double x2 = Double.parseDouble(tokens[4]);
                double y2 = Double.parseDouble(tokens[5]);

                Area area = new Area(x1, x2, y1, y2);

                if (type.equals("Fauna")) {
                    double forca = Double.parseDouble(tokens[6]);
                    int timesReproduced = Integer.parseInt(tokens[7]);
                    int segundosParaReproduzir = Integer.parseInt(tokens[8]);

                    Fauna fauna = new Fauna(ecossistema);
                    fauna.setId(id);
                    fauna.setArea(area);
                    fauna.setForca(forca);
                    fauna.setTimesReproduced(timesReproduced);
                    fauna.setSegundosParaReproduzir(segundosParaReproduzir);
                    elementos.add(fauna);
                } else if (type.equals("Flora")) {
                    double forca = Double.parseDouble(tokens[6]);
                    int timesReproduced = Integer.parseInt(tokens[7]);

                    Flora flora = new Flora(ecossistema);
                    flora.setId(id);
                    flora.setArea(area);
                    flora.setForca(forca);
                    flora.setTimesReproduced(timesReproduced);
                    elementos.add(flora);
                } else if (type.equals("Inanimado")) {
                    Inanimado inanimado = new Inanimado();
                    inanimado.setId(id);
                    inanimado.setArea(area);
                    elementos.add(inanimado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return elementos;
    }
}
