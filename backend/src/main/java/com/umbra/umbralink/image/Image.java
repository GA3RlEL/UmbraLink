package com.umbra.umbralink.image;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.umbra.umbralink.message.Message;
import com.umbra.umbralink.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publicId;

    private String url = null;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    @JsonBackReference
    private UserEntity user = null;

    @ManyToOne
    @JoinColumn(name = "user_photo_message_id")
    @JsonBackReference
    private UserEntity photoMessageUser = null;

    @OneToOne
    @JoinColumn(name = "message_id")
    @JsonBackReference
    private Message message = null;
}
