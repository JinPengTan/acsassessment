package com.assessment.acs.services;

public interface LibraryServices {

    void borrowBooks(String email, String isbn);

    void returnBooks(String email, String isbn);
}
