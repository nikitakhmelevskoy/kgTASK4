package com.cgvsu.render_engine;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.model.ModelOnScene;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.Point2f;
import java.util.ArrayList;

import static com.cgvsu.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;
import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class RenderEngine {
    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final ModelOnScene mesh,
            final int width,
            final int height) throws Vector.VectorException, Matrix.MatrixException {

        Matrix4f modelMatrix = mesh.rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix = (Matrix4f) Matrix.multiplyMatrices(viewMatrix, modelViewProjectionMatrix);
        modelViewProjectionMatrix = (Matrix4f) Matrix.multiplyMatrices(projectionMatrix, modelViewProjectionMatrix);

        final int nPolygons = mesh.getPolygons().size();

        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.getPolygons().get(polygonInd).getVertexIndices().size();
            ArrayList<Point2f> resultPoints = new ArrayList<>();

            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                Vector3f vertex2 = new Vector3f(vertex.getX(), vertex.getY(), vertex.getZ());

                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex2), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).x,
                        resultPoints.get(vertexInPolygonInd - 1).y,
                        resultPoints.get(vertexInPolygonInd).x,
                        resultPoints.get(vertexInPolygonInd).y
                );
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x,
                        resultPoints.get(0).y
                );
        }
    }
}