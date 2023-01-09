package com.cgvsu.model;

import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.objreader.ReaderExceptions;

import java.util.ArrayList;
import java.util.List;

public class Model {
    protected List<Vector3f> vertices;
    protected List<Vector2f> textureVertices;
    protected List<Vector3f> normals;
    protected List<Polygon> polygons;

    public Model(
            final List<Vector3f> vertices,
            final List<Vector2f> textureVertices,
            final List<Vector3f> normals,
            final List<Polygon> polygons) {
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.normals = normals;
        this.polygons = polygons;
    }

    public Model() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public Model(Model model) {
        this.vertices = model.vertices;
        this.textureVertices = model.textureVertices;
        this.normals = model.normals;
        this.polygons = model.polygons;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setVertices(final List<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public void setTextureVertices(final List<Vector2f> vertices) {
        this.textureVertices = vertices;
    }

    public void setNormals(final List<Vector3f> vertices) {
        this.normals = vertices;
    }

    public void setPolygons(final List<Polygon> vertices) {
        this.polygons = vertices;
    }

    public boolean checkConsistency() {
        for (int i = 0; i < polygons.size(); i++) {
            List<Integer> vertexIndices = polygons.get(i).getVertexIndices();
            List<Integer> textureVertexIndices = polygons.get(i).getTextureVertexIndices();
            List<Integer> normalIndices = polygons.get(i).getNormalIndices();

            if (vertexIndices.size() != textureVertexIndices.size()
                    && vertexIndices.size() != 0 && textureVertexIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "Унифицированный формат для указания описаний полигонов не определен."
                );
            }

            if (vertexIndices.size() != normalIndices.size()
                    && vertexIndices.size() != 0 && normalIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "Унифицированный формат для указания описаний полигонов не определен."
                );
            }

            if (normalIndices.size() != textureVertexIndices.size()
                    && normalIndices.size() != 0 && textureVertexIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "Унифицированный формат для указания описаний полигонов не определен."
                );
            }

            for (Integer vertexIndex : vertexIndices) {
                if (vertexIndex >= vertices.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Описание полигона неверно.", i + 1
                    );
                }
            }

            for (Integer textureVertexIndex : textureVertexIndices) {
                if (textureVertexIndex >= textureVertices.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Описание полигона неверно.", i + 1
                    );
                }
            }

            for (Integer normalIndex : normalIndices) {
                if (normalIndex >= normals.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Описание полигона неверно.", i + 1
                    );
                }
            }
        }

        return true;
    }
}
