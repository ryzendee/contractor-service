package ryzendee.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * Представляет контрагента в системе и содержит основную информацию о нем
 *
 * @author Dmitry Ryazantsev
 */
@Getter
@Setter
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class Contractor {

    @Id
    private String id;
    private String parentId;
    private String name;
    private String nameFull;
    private String inn;
    private String ogrn;
    private String country;
    private Integer industry;
    private Integer orgForm;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String createUserId;
    private String modifyUserId;
    private Boolean isActive;
}
