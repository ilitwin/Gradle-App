package com.app.core.api.model;

import com.app.core.api.service.utils.ClockUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class BaseCollection implements Serializable {

    @Id
    protected String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected Date createdDate = ClockUtils.getNow();


    public void setAllToNull(){
        this.createdDate = null;
    }
}
