package ru.job4j.pooh;

enum HttpStatusCode {
    OK("200"), NO_CONTENT("204");
    private final String status;

    HttpStatusCode(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
