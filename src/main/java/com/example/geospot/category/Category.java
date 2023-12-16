package com.example.geospot.category;

import com.example.geospot.validation.EmojiSymbol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @EmojiSymbol
    private String symbol;
    @NotNull
    private String description;

    public Category(String name, String symbol, String description) {
        this.name = name;
        this.symbol = symbol;
        this.description = description;
    }
}
