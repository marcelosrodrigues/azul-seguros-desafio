package com.infobase.desafio.azul.entities.embeddables;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ApiModel(value = "Endereco do Cliente")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Endereco implements Serializable {

    @ApiModelProperty(name = "logradouro", value = "Logradouro", required = true, dataType = "string")
    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    @ApiModelProperty(name = "numero", value = "Numero", required = true, dataType = "string")
    @NonNull
    private String numero;

    @Column(nullable = true)
    @ApiModelProperty(name = "complemento", value = "Complemento", required = false, dataType = "string")
    @NonNull
    private String complemento;

    @Column(nullable = false)
    @ApiModelProperty(name = "bairro", value = "Bairro", required = true, dataType = "string")
    private String bairro;

    @Column(nullable = false)
    @ApiModelProperty(name = "cep", value = "CEP", required = true, dataType = "string")
    @NonNull
    private String cep;

    @Column(nullable = false)
    @ApiModelProperty(name = "uf", value = "Estado", required = true, dataType = "string")
    private String uf;

    @Column(nullable = false)
    @ApiModelProperty(name = "localidade", value = "Cidade", required = true, dataType = "string")
    private String localidade;

    @ApiModelProperty(name = "ibge", value = "Código IBGE", required = true, dataType = "string")
    @Column(nullable = false)
    private String ibge;

    @ApiModelProperty(name = "gia", value = "gia", required = true, dataType = "string")
    @Column(nullable = false)
    private String gia;

    @ApiModelProperty(name = "ddd", value = "Código DDD", required = true, dataType = "string")
    @Column(nullable = false)
    private String ddd;

    @ApiModelProperty(name = "siafi", value = "siafi", required = true, dataType = "string")
    @Column(nullable = false)
    private String siafi;

    public Endereco(String logradouro, String numero, String complemento, String bairro, String cep, String uf, String localidade, String ibge, String gia, String ddd, String siafi) {
        this();
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.uf = uf;
        this.localidade = localidade;
        this.ibge = ibge;
        this.gia = gia;
        this.ddd = ddd;
        this.siafi = siafi;
    }


}
