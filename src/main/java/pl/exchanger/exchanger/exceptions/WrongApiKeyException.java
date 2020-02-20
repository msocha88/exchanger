package pl.exchanger.exchanger.exceptions;

public class WrongApiKeyException extends RuntimeException {

    public WrongApiKeyException(String apiKey) {

        super("API key: " + apiKey + " is invalid. Please enter proper API key.");
    }
}
