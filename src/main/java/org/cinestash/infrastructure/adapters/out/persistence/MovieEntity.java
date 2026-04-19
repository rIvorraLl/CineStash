package org.cinestash.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String director;
    private String mainActors;
    private LocalDate dateOfView;
    @Column(columnDefinition = "TEXT")
    private String review;
    private Integer stars;
    @Column(columnDefinition = "TEXT")
    private String imageData;
}