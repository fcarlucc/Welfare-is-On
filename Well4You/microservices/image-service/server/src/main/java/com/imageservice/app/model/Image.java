package com.imageservice.app.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an image entity stored in the database.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image", schema = "imageservice")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private long size;

    /**
     * Constructs an Image object with the given attributes.
     * @param name The name of the image.
     * @param path The path where the image is stored.
     * @param type The type or format of the image.
     * @param size The size of the image in bytes.
     */
    public Image(String name, String path, String type, long size) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.size = size;
    }

    /**
     * Returns a string representation of the Image object.
     * @return A string representing the Image object's state.
     */
    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                '}';
    }
}
