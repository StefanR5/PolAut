package com.polovni_automobili.auta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchFormDto {

    private Long markaId;
    private Long modelId;
    private Long gorivoId;
    private Long menjacId;
    private Long karoserijaId;
    private Integer broj_sjedista;
    private Long city;
    private String godina;
    private Integer kilometraza;
    private Integer kubikaza;
    private Integer snaga;

}
