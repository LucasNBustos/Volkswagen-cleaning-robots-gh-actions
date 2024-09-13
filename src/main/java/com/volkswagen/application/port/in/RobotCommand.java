package com.volkswagen.application.port.in;

public enum RobotCommand {
    L, R, M;

    public static RobotCommand fromChar(char rawValue) {
        return switch (rawValue) {
            case 'L' -> L;
            case 'R' -> R;
            case 'M' -> M;
            default -> throw new IllegalArgumentException("Invalid command: " + rawValue);
        };
    }
}

