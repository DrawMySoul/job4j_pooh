package ru.job4j.pooh;

enum HttpRequest {
    GET("GET"), POST("POST");
    private final String request;

    HttpRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }
}
