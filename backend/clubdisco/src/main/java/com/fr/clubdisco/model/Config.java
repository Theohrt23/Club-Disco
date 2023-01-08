package com.fr.clubdisco.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "config")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column
    @NonNull
    private Long userId;

    @Column()
    private String zippoptam_zip_code;

    @Column()
    private String zippopotam_region;

    @Column()
    private String weather_city;

    public Config (Long userId){
        this.userId = userId;
        this.zippoptam_zip_code = null;
        this.zippopotam_region = null;
        this.weather_city = null;
    }

}
