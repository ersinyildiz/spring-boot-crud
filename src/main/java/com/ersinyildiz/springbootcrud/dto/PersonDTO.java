package com.ersinyildiz.springbootcrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    @Size(max = 30, min = 3, message = "Lütfen geçerli bir ad-soyad giriniz..")
    private String name;
    @Email(message = "Lütfen geçerli bir eposta adresi giriniz..")
    @Length(min = 5, max = 200,message = "Email uzunluğu 5 ile 200 karakter arası olmalıdır..")
    private String email;
    @Size(min = 11, max = 11,message = "Lütfen geçerli bir tel. no giriniz..")
    private String phone;
}
