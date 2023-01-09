package com.cgvsu.objreader;

public class ReaderExceptions extends Throwable {
    public static class ObjReaderException extends RuntimeException {
        public ObjReaderException(final String errorMessage, final int lineInd) {
            super("Ошибка считывания Obj: " + lineInd + ". " + errorMessage);
        }
    }

    public static class NotDefinedUniformFormatException extends RuntimeException {
        public NotDefinedUniformFormatException(final String errorMessage) {
            super("Ошибка, формат не универсален: " + errorMessage);
        }
    }

    public static class FaceException extends RuntimeException {
        public FaceException(final String errorMessage, final int ordinalNumberOfFace) {
            super("Какая-то грань неправильная, вершина не существует: " + ordinalNumberOfFace + ". " + errorMessage);
        }
    }

    public static class WrongFileException extends RuntimeException {
        public WrongFileException(final String errorMessage) {
            super("Ошибка, не тот файл: " + errorMessage);
        }
    }
}
