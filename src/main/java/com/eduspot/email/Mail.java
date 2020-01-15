package com.eduspot.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
}
