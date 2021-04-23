package com.infobase.desafio.azul.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.infobase.desafio.azul.entities.embeddables.Endereco;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(description = "Cliente")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", dataType = "long", readOnly = true)
    private Long id;

    @Column(name = "nome" , nullable = false)
    @ApiModelProperty(value = "nome", dataType = "String", required = true)
    @NonNull
    private String nome;

    @Column(name = "email", unique = true, nullable = false)
    @ApiModelProperty(value = "email", dataType = "String", required = true)
    @NonNull
    private String email;

    @Embedded
    @ApiModelProperty(value = "endereco", required = true)
    @NonNull
    private Endereco endereco;



}
