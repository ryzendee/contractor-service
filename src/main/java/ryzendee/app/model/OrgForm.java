package ryzendee.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Представляет организационную  форму контрагента.
 *
 * author Dmitry Ryazantsev
 */
@Getter
@Setter
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class OrgForm {

    @Id
    private Integer id;
    private String name;
    private Boolean isActive;

}
