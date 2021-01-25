package com.ersinyildiz.springbootcrud.model;


import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //Used In DefaultDataLoader.java
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 30, min = 3, message = "Lütfen isim soyisim giriniz..")
    private String name;
    @Email(message = "Lütfen geçerli bir eposta adresi giriniz..")
    @Length(min = 5, max = 200,message = "Email uzunluğu 5 ile 200 karakter arası olmalıdır..")
    private String email;
    @Size(min = 11, max = 11,message = "Lütfen geçerli bir numara giriniz..")
    private String phone;
}
