package com.cgvsu;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.ModelOnScene;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.scene.Scene;
import com.cgvsu.writer.ObjWriter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public class GuiController {
    final private float TRANSLATION = 0.1F;
    final private float SCALE = 1F;
    final private float ROTATION = 1F;

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    private Timeline timeline;
    Scene scene = new Scene();
    private double anchorX, anchorY;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty()
                .addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty()
                .addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.setOnScroll(scrollEvent -> {
                zoom(scrollEvent);
            });
            canvas.setOnMousePressed(mouseEvent -> {
                mousePressed(mouseEvent);
            });
            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            scene.camera.setAspectRatio((float) (width / height));

            for (ModelOnScene model : scene.modelsList) {
                try {
                    RenderEngine.render(canvas.getGraphicsContext2D(), scene.camera, model, (int) width, (int) height);
                } catch (Vector.VectorException | Matrix.MatrixException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    private void mousePressed(MouseEvent mouseEvent) {
        AtomicReference<Double> startX = new AtomicReference<>(mouseEvent.getX());
        AtomicReference<Double> startY = new AtomicReference<>(mouseEvent.getY());

        canvas.setOnMouseDragged(mouseEvent1 -> {
            double endX = mouseEvent1.getX();
            double endY = mouseEvent1.getY();
            double dx = startX.get() - endX;
            double dy = endY - startY.get();
            double dz = dx;

            if (scene.getCamera().getPosition().getZ() < 0) {
                dx *= -1;
            }

            if (scene.getCamera().getPosition().getX() > 0) {
                dz *= -1;
            }

            if (Math.abs(dy) > Math.abs(dx)) {
                dz *= 0;
            }

            startX.set(endX);
            startY.set(endY);

            try {
                scene.getCamera().movePosition(
                        new Vector3f(new float[]{(float) dx * 0.01F,
                                (float) dy * 0.01F,
                                (float) dz * 0.01F})
                );
            } catch (Vector.VectorException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            ModelOnScene model = new ModelOnScene(ObjReader.read(fileContent, false));
            scene.modelsList.add(model);
        } catch (IOException exception) {
        }
    }

    @FXML
    public void onSaveModelMenuItemClick(ActionEvent actionEvent) {
        FileChooser file = new FileChooser();
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (.obj)", ".obj"));
        file.setTitle("Save Model");
        File newFile = file.showOpenDialog(canvas.getScene().getWindow());

        if (newFile == null) {
            return;
        }

        try {
            Model model;
            model = scene.modelsList.get(0);
            ObjWriter.writeToFile(model, newFile);
        } catch (Exception exception) {
            System.out.println("pupupu");
        }

    }


    @FXML
    public void zoom(ScrollEvent scrollEvent) {
        double delta = scrollEvent.getDeltaY();
        Vector3f cameraPositionInCoords = scene.getCameraPosition();
        if (delta < 0) {
            try {
                if (cameraPositionInCoords.getX() > cameraPositionInCoords.getY()
                        && cameraPositionInCoords.getX() > cameraPositionInCoords.getZ()) {
                    scene.getCamera().movePosition(new Vector3f(new float[]{TRANSLATION, 0, 0}));
                } else if (cameraPositionInCoords.getY() > cameraPositionInCoords.getX()
                        && cameraPositionInCoords.getY() > cameraPositionInCoords.getZ()) {
                    scene.getCamera().movePosition(new Vector3f(new float[]{0, TRANSLATION, 0}));
                } else if (cameraPositionInCoords.getZ() > cameraPositionInCoords.getX()
                        && cameraPositionInCoords.getZ() > cameraPositionInCoords.getY()) {
                    scene.getCamera().movePosition(new Vector3f(new float[]{0, 0, TRANSLATION}));
                }
            } catch (Vector.VectorException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                if (cameraPositionInCoords.getX() > cameraPositionInCoords.getY()
                        && cameraPositionInCoords.getX() > cameraPositionInCoords.getZ()) {
                    scene.getCamera().movePosition(new Vector3f(new float[]{-TRANSLATION, 0, 0}));
                } else if (cameraPositionInCoords.getY() > cameraPositionInCoords.getX()
                        && cameraPositionInCoords.getY() > cameraPositionInCoords.getZ()) {
                    scene.getCamera().movePosition(new Vector3f(new float[]{0, -TRANSLATION, 0}));
                } else if (cameraPositionInCoords.getZ() > cameraPositionInCoords.getX()
                        && cameraPositionInCoords.getZ() > cameraPositionInCoords.getY()) {
                    scene.getCamera().movePosition(new Vector3f(new float[]{0, 0, -TRANSLATION}));
                }
            } catch (Vector.VectorException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initMouseControl(Canvas canvas, Camera camera) {
        canvas.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
        });

        canvas.setOnMouseDragged(event -> {
            //scene.camera.setPosition(new Vector3f((float) ((float) event.getSceneX() + vanchorX), (float) ((float) event.getSceneY() + anchorY), camera.getPosition().get(2)));
        });
    }

    @FXML
    public void handleModelLeft(ActionEvent actionEvent) {
        for (ModelOnScene model : scene.modelsList) {
            model.setTranslationX(TRANSLATION);
            try {
                RenderEngine.render(canvas.getGraphicsContext2D(), scene.camera, model, (int) canvas.getWidth(),
                        (int) canvas.getHeight());
            } catch (Vector.VectorException | Matrix.MatrixException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void handleModelRight(ActionEvent actionEvent) {
        for (ModelOnScene model : scene.modelsList) {
            model.setTranslationX(-TRANSLATION);
            try {
                RenderEngine.render(canvas.getGraphicsContext2D(), scene.camera, model, (int) canvas.getWidth(),
                        (int) canvas.getHeight());
            } catch (Vector.VectorException | Matrix.MatrixException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void handleModelBackward(ActionEvent actionEvent) {
        for (ModelOnScene model : scene.modelsList) {
            model.setTranslationY(-TRANSLATION);
            try {
                RenderEngine.render(canvas.getGraphicsContext2D(), scene.camera, model, (int) canvas.getWidth(),
                        (int) canvas.getHeight());
            } catch (Vector.VectorException | Matrix.MatrixException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void handleModelDown(ActionEvent actionEvent) {
        for (ModelOnScene model : scene.modelsList) {
            model.setTranslationZ(-TRANSLATION);
            try {
                RenderEngine.render(canvas.getGraphicsContext2D(), scene.camera, model, (int) canvas.getWidth(),
                        (int) canvas.getHeight());
            } catch (Vector.VectorException | Matrix.MatrixException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void handleModelForward(ActionEvent actionEvent) {
        for (ModelOnScene model : scene.modelsList) {
            model.setTranslationY(TRANSLATION);
            try {
                RenderEngine.render(canvas.getGraphicsContext2D(), scene.camera, model, (int) canvas.getWidth(),
                        (int) canvas.getHeight());
            } catch (Vector.VectorException | Matrix.MatrixException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void handleModelUp(ActionEvent actionEvent) throws Matrix.MatrixException, Vector.VectorException {
        for (ModelOnScene model : scene.modelsList) {
            model.setTranslationZ(TRANSLATION);
            RenderEngine.render(canvas.getGraphicsContext2D(), scene.camera, model, (int) canvas.getWidth(),
                    (int) canvas.getHeight());
        }
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        try {
            scene.camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
        } catch (Vector.VectorException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        try {
            scene.camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
        } catch (Vector.VectorException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        try {
            scene.camera.movePosition(new Vector3f(0, TRANSLATION, 0));
        } catch (Vector.VectorException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        try {
            scene.camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
        } catch (Vector.VectorException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        try {
            scene.camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
        } catch (Vector.VectorException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        try {
            scene.camera.movePosition(new Vector3f(0, 0, TRANSLATION));
        } catch (Vector.VectorException e) {
            throw new RuntimeException(e);
        }
    }
}