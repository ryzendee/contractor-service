package ryzendee.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Представляет страну контрагента.
 *
 * @author Dmitry Ryazantsev
 */
@Getter
@Setter
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    private String id;
    private String name;
    private Boolean isActive;
}
