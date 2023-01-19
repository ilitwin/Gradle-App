package com.app.core.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Campaign extends BaseCollection {
    private UUID uuid;

    private String companyId;

    private String name;

    private String description;
}
