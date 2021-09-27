package edu.eam.ingesoft.ejemploPersons.exceptions

class BusinessException(message: String) : RuntimeException(message)

/**
 * public class BusinessException extends RuntimeException {
 *     public BusinessException(String message) {
 *          super(message);
 *     }
 * }
 */