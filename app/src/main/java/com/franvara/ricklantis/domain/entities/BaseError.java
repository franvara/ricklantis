package com.franvara.ricklantis.domain.entities;

public class BaseError {

    private static final String STANDARD_ERROR_MESSSAGE = "Hubo un error inesperado";
    private static final int STANDARD_ERROR_CODE = -1;
    private static final String CONNECTION_ERROR_MESSSAGE = "Funcionalidad no disponible sin conexión";
    private static final int CONNECTION_ERROR_CODE = -2;

    private int errorCode;
    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public BaseError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static BaseError getStandardError() {
        return new BaseError(STANDARD_ERROR_CODE, STANDARD_ERROR_MESSSAGE);
    }

    public static BaseError getConnectionError() {
        return new BaseError(CONNECTION_ERROR_CODE, CONNECTION_ERROR_MESSSAGE);
    }
}
