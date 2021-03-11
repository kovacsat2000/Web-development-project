package inf.unideb.hu.dao;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Table(name = "country", schema = "sakila")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="country_id")
    private int countryId;

    @Column(name = "country")
    private String country;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
}