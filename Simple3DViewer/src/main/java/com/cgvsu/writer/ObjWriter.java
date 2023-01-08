package com.cgvsu.writer;

import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ObjWriter {
    public static void createObjFile(String absoluteFilePath) throws IOException {
        String fileSeparator = System.getProperty("file.separator");
        absoluteFilePath += fileSeparator + "file.obj";
        File file = new File(absoluteFilePath);
    }

    public static void writeToFile(Model model, File file) throws IOException {
        String str = "";

        str += writeVertexes(model.getVertices());
        str += writeTextureVertexes(model.getTextureVertices());
        str += writeNormals(model.getNormals());
        str += writePolygons(model.getPolygons());

        toFile(str, file.getAbsolutePath());
    }

    protected static void toFile(String line, String fileName) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(fileName);
        printWriter.print(line);
        printWriter.close();
    }

    protected static String writeVertexes(final List<Vector3f> v) {
        String str = "";

        for (Vector3f vector3f : v) {
            final String vx = String.format("%.4f", vector3f.getX()).replace(',', '.');
            final String vy = String.format("%.4f", vector3f.getY()).replace(',', '.');
            final String vz = String.format("%.4f", vector3f.getZ()).replace(',', '.');
            str = str + "v  " + vx + " " + vy + " " + vz + "\n";
        }

        str = str + "# " + v.size() + " vertices";
        str += "\n";
        str += "\n";
        return str;
    }

    protected static String writeTextureVertexes(final List<Vector2f> vt) {
        String str = "";

        for (Vector2f vector2f : vt) {
            final String vtx = String.format("%.4f", vector2f.getX()).replace(',', '.');
            final String vty = String.format("%.4f", vector2f.getY()).replace(',', '.');
            str = str + "vt " + vtx + " " + vty + " " + "0.0000" + "\n";
        }

        str = str + "# " + vt.size() + " texture coords";
        str += "\n";
        str += "\n";
        return str;
    }

    protected static String writeNormals(final List<Vector3f> vn) {
        String str = "";

        for (Vector3f vector3f : vn) {
            final String vx = String.format("%.4f", vector3f.getX()).replace(',', '.');
            final String vy = String.format("%.4f", vector3f.getY()).replace(',', '.');
            final String vz = String.format("%.4f", vector3f.getZ()).replace(',', '.');
            str = str + "vn  " + vx + " " + vy + " " + vz + "\n";
        }

        str = str + "# " + vn.size() + " normals";
        str += "\n";
        str += "\n";
        return str;
    }

    protected static String writePolygons(final List<Polygon> p) {
        String str = "";

        for (Polygon polygon : p) {
            str = str + "f ";
            final Polygon pol = polygon;

            for (int j = 0; j < pol.getVertexIndices().size(); j++) {
                if (!pol.getTextureVertexIndices().isEmpty() && pol.getNormalIndices().isEmpty()) {
                    str = str + (pol.getVertexIndices().get(j) + 1) + "/"
                            + (pol.getTextureVertexIndices().get(j) + 1) + " ";
                }

                if (pol.getTextureVertexIndices().isEmpty() && pol.getNormalIndices().isEmpty()) {
                    str = str + (pol.getVertexIndices().get(j) + 1) + " ";
                }

                if (!pol.getTextureVertexIndices().isEmpty() && !pol.getNormalIndices().isEmpty()) {
                    str = str + (pol.getVertexIndices().get(j) + 1) + "/"
                            + (pol.getTextureVertexIndices().get(j) + 1) + "/"
                            + (pol.getNormalIndices().get(j) + 1) + " ";
                }

                if (pol.getTextureVertexIndices().isEmpty() && !pol.getNormalIndices().isEmpty()) {
                    str = str + (pol.getVertexIndices().get(j) + 1) + "//"
                            + (pol.getNormalIndices().get(j) + 1) + " ";
                }
            }

            str = str + "\n";
        }

        str = str + "# " + p.size() + " polygons";
        str += "\n";
        str += "\n";
        return str;
    }
}
