package com.MisRaices.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String nombre;
    private String apellido;
    private Long telefono;
    @Email
    @Column( unique = true)
    private String correo;
    @NotNull
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
    private boolean activo;
    private String codigo;//codigo de activacion de cuenta
    private String token;//token para restablecer contraceña
    private LocalDateTime tokenLimite; //tiempo de token
    private String imgUri;
}