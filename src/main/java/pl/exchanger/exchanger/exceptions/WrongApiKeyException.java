package pl.exchanger.exchanger.exceptions;

public class WrongApiKeyException extends RuntimeException {

    public WrongApiKeyException() {

        super("Your API Key is invalid. Please enter proper API Key.");
    }
}
