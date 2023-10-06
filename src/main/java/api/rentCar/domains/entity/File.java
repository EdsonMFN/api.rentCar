package api.rentCar.domains.entity;

import api.rentCar.enums.TypeFile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Entity()
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_file")
    @PrimaryKeyJoinColumns(value = {@PrimaryKeyJoinColumn})
    private Long id;

    @Column(name = "Type_file")
    @Enumerated(EnumType.STRING)
    private TypeFile typeFile;

    @Column(name = "base64")
    private Base64 base64;
}
