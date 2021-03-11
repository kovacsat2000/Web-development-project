package inf.unideb.hu.dao;

import lombok.*;
import org.hibernate.type.BlobType;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "staff", schema = "sakila")
public class StaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private int staffId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Column(name = "picture")
    private Blob picture;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @Column(name = "active")
    private int active;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

}