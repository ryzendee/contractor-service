package ryzendee.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Представляет индустриальный код контрагента.
 *
 * @author Dmitry Ryazantsev
 */
@Getter
@Setter
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class Industry {

    @Id
    private Integer id;
    private String name;
    private Boolean isActive;
}
